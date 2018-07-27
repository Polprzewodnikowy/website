package pl.polprzewodnikowy.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    private SessionFactory sessionFactory;

    private UserService(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public UserInfo getCurrentUserInfo() {
        String name = getAuthentication().getName();
        return getUserByUsername(name);
    }

    public void addUser(UserInfo user) {
        userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        UserInfo userInfo = getUserByUsername(username);
        userRepository.delete(userInfo);
    }

    public UserInfo getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public UserInfo getUserByUsername(String username) {
        UserInfo userInfo;

        Session session = sessionFactory.openSession();
        try {
            Query<UserInfo> query = session.createQuery("from UserInfo where user_name =:username", UserInfo.class);
            query.setParameter("username", username);
            userInfo = query.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }

        return userInfo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = getUserByUsername(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("Not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole role : userInfo.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        }

        return new User(userInfo.getName(), userInfo.getPassword(), authorities);
    }

    public void addUserInfoToModel(Model model) {
        String name = getAuthentication().getName();
        UserInfo userInfo = getUserByUsername(name);
        if (userInfo != null) {
            model.addAttribute("userInfo", userInfo);
        }
    }

    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
