package pl.polprzewodnikowy.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polprzewodnikowy.settings.SettingsService;
import pl.polprzewodnikowy.user.UserService;

@Controller
@RequestMapping("/blog")
class BlogController {

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

    @RequestMapping("")
    private String blog(Model model) {
        return "blog";
    }

}
