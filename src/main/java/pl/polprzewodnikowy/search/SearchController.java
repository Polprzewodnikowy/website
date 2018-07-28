package pl.polprzewodnikowy.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polprzewodnikowy.settings.SettingsService;
import pl.polprzewodnikowy.user.UserService;

@Controller
class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private SettingsService settingsService;

    @ModelAttribute
    private void userInfo(Model model) {
        userService.addUserInfoToModel(model);
    }

    @ModelAttribute
    private void websiteSettings(Model model) {
        settingsService.addWebsiteSettingsToModel(model);
    }

    @GetMapping("/search")
    private String search(@RequestParam("q") String query, @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        return "search";
    }

}
