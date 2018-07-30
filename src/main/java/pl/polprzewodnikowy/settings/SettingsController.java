package pl.polprzewodnikowy.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polprzewodnikowy.user.UserService;

@Controller
class SettingsController {

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

    @GetMapping("/admin")
    private String admin(Model model) {
        return "admin";
    }

    @PostMapping("/admin/save")
    private String admin(@RequestParam String websiteTitle,
                         @RequestParam String facebookLink,
                         @RequestParam String twitterLink,
                         @RequestParam String githubLink,
                         @RequestParam String linkedinLink,
                         @RequestParam String authorLore) {
        settingsService.setSetting(new Setting("websiteTitle", websiteTitle));
        settingsService.setSetting(new Setting("facebookLink", facebookLink));
        settingsService.setSetting(new Setting("twitterLink", twitterLink));
        settingsService.setSetting(new Setting("githubLink", githubLink));
        settingsService.setSetting(new Setting("linkedinLink", linkedinLink));
        settingsService.setSetting(new Setting("authorLore", authorLore));
        return "redirect:/admin?success=true";
    }

}
