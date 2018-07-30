package pl.polprzewodnikowy.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polprzewodnikowy.blog.BlogService;
import pl.polprzewodnikowy.blog.Entry;
import pl.polprzewodnikowy.settings.SettingsService;
import pl.polprzewodnikowy.user.UserService;

import java.util.List;

@Controller
class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

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

        //TODO: implement user and tag search

        if (query.startsWith("@")) {                                            // search users
//          List<UserInfo> users = userService.searchUsers(query, page);
//          model.addAtribute("usersResults", users);
        } else if (query.startsWith("#")) {                                     // search entries by tag
//          List<Entry> entries = blogService.searchEntriesByTag(query, page);
//          model.addAttribute("entriesResults", entries);
        } else {                                                                // search entries by body
            List<Entry> entries = blogService.searchEntries(query, page);
            model.addAttribute("entriesResults", entries);
        }

        model.addAttribute("currentPage", page);

        return "search";
    }

}
