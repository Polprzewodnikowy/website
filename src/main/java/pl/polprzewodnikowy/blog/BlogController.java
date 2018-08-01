package pl.polprzewodnikowy.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.polprzewodnikowy.settings.SettingsService;
import pl.polprzewodnikowy.user.UserService;

@Controller
class BlogController {

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

    @GetMapping("/blog")
    private String blog(Model model) {
        return blogPage(1, model);
    }

    @GetMapping("/blog/{page}")
    private String blogPage(@PathVariable Integer page, Model model) {
        blogService.addPageNumbersToModel(model);
        model.addAttribute("currentPage", page);
        model.addAttribute("entries", blogService.getPage(page));
        return "blog";
    }

    @GetMapping("/blog/entry/{id}")
    private String blogEntryId(@PathVariable Integer id, Model model) {
        model.addAttribute("entry", blogService.getEntryByIdFormatted(id));
        return "entry";
    }

    @GetMapping("/blog/edit")
    private String blogEdit(Model model) {
        model.addAttribute("entry", new Entry());
        return "blog-edit";
    }

    @GetMapping("/blog/edit/{id}")
    private String blogEditId(@PathVariable Integer id, Model model) {
        model.addAttribute("entry", blogService.getEntryByIdUnformatted(id));
        return "blog-edit";
    }

    @PostMapping("/blog/edit")
    private String blogEdit(@ModelAttribute Entry entry) {
        blogService.addOrEditEntry(entry);
        return "redirect:/blog/entry/" + entry.getId();
    }

    @ResponseBody
    @PostMapping("/blog/edit/preview")
    private Entry blogEditPreview(@ModelAttribute Entry entry) {
        return blogService.prepareEntryFormatting(entry);
    }

    @GetMapping("/blog/delete/{id}")
    private String blogDelete(@PathVariable Integer id) {
        blogService.deleteEntryById(id);
        return "redirect:/blog?delete=true";
    }

}
