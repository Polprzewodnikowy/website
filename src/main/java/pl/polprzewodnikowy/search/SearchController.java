package pl.polprzewodnikowy.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polprzewodnikowy.settings.SettingsService;
import pl.polprzewodnikowy.user.UserService;

@Controller
@RequestMapping("/search")
class SearchController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userInfo(Model model) {
        userService.addUserInfoToModel(model);
    }

    @Autowired
    private SettingsService settingsService;

    @ModelAttribute
    private void websiteTitle(Model model) {
        settingsService.addWebsiteSettingsToModel(model);
    }

    @RequestMapping("")
    private String search(@RequestParam("q") String query, Model model) {
        return "search";
    }

}
