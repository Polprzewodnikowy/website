package pl.polprzewodnikowy.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserService implements UserDetailsService, InitializingBean {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private SessionFactory sessionFactory;

    private UserService(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public UserInfo getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public UserInfo getUserByUsername(String username) {
        UserInfo userInfo;

        try(Session session = sessionFactory.openSession()) {
            Query<UserInfo> query = session.createQuery("from UserInfo where user_name = ?1", UserInfo.class);
            query.setParameter(1, username);
            userInfo = query.uniqueResult();
        } catch (Exception e) {
            throw e;
        }

        return userInfo;
    }

    public void addOrEditUser(UserInfo user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.delete(getUserByUsername(username));
    }

    public boolean changeUserPassword(String username, String oldPassword, String newPassword) {
        UserInfo userInfo = getUserByUsername(username);
        if(passwordEncoder.matches(oldPassword, userInfo.getPassword())) {
            userInfo.setPassword(newPassword);
            addOrEditUser(userInfo);
        } else {
            return false;
        }
        return true;
    }

    public boolean userHasRole(UserInfo user, UserRole role) {
        return user.getRoles().contains(role);
    }

    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    public UserInfo getCurrentUser() {
        String name = getAuthentication().getName();
        return getUserByUsername(name);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void addCurrentUserInfoToModel(Model model) {
        UserInfo userInfo = getCurrentUser();
        if (userInfo != null) {
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("isUserAdmin", userInfo.getRoles().contains(UserRole.ADMIN));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = getUserByUsername(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole role : userInfo.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        }

        return new User(userInfo.getName(), userInfo.getPassword(), authorities);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Create admin account if it doesn't exist
        if(!userRepository.existsById(1)) {
            UserInfo admin = new UserInfo("admin", passwordEncoder.encode("admin"));
            admin.setId(1);
            admin.addRole(UserRole.USER);
            admin.addRole(UserRole.ADMIN);
            userRepository.save(admin);
        }
    }

}
