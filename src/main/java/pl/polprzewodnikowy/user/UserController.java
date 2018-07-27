package pl.polprzewodnikowy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polprzewodnikowy.settings.SettingsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SettingsService settingsService;

    @ModelAttribute
    private void userInfo(Model model) {
        userService.addUserInfoToModel(model);
    }

    @ModelAttribute
    private void websiteTitle(Model model) {
        settingsService.addWebsiteSettingsToModel(model);
    }

    @RequestMapping("/user")
    private String user() {
        return "user";
    }

    @RequestMapping("/login")
    private String login() {
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    private String logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logoutUser(request, response);
        return "redirect:/login?logout=true";
    }

}
