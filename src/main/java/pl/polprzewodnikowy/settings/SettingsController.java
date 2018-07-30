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
    private String admin(@RequestParam String websiteTitle, @RequestParam String githubLink, @RequestParam String linkedinLink) {
        settingsService.setWebsiteTitle(websiteTitle);
        settingsService.setGithubLink(githubLink);
        settingsService.setLinkedinLink(linkedinLink);
        return "redirect:/admin?success=true";
    }

}
