package pl.polprzewodnikowy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        userService.addCurrentUserInfoToModel(model);
    }

    @ModelAttribute
    private void websiteSettings(Model model) {
        settingsService.addWebsiteSettingsToModel(model);
    }

    @GetMapping("/user/edit")
    private String userEdit(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "user-edit";
    }

    @PostMapping("/user/edit")
    private String userEdit(@RequestParam(name = "oldPassword") String oldPassword,
                            @RequestParam(name = "newPassword") String newPassword) {
        if(userService.changeUserPassword(userService.getCurrentUser().getName(), oldPassword, newPassword)) {
            return "redirect:/user/edit?changed=true";
        } else {
            return "redirect:/user/edit?incorrect=true";
        }
    }

    @GetMapping("/user")
    private String user() {
        return "redirect:/user/" + userService.getCurrentUser().getName();
    }

    @GetMapping("/user/{user}")
    private String user(@PathVariable String user, Model model) {
        model.addAttribute("user", userService.getUserByUsername(user));
        return "user";
    }

    @GetMapping("/login")
    private String login() {
        if (userService.getCurrentUser() != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/logout")
    private String logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logoutUser(request, response);
        return "redirect:/login?logout=true";
    }

}
