package pl.polprzewodnikowy.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.polprzewodnikowy.settings.SettingsService;
import pl.polprzewodnikowy.user.UserService;

import java.util.Date;
import java.util.List;

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
        List<Entry> entries = blogService.getPage(page);
        blogService.addPageNumbersToModel(model);
        model.addAttribute("currentPage", page);
        model.addAttribute("entries", entries);
        return "blog";
    }

    @GetMapping("/blog/entry/{id}")
    private String blogEntryId(@PathVariable Integer id, Model model) {
        Entry entry = blogService.getEntryById(id);
        model.addAttribute("entry", entry);
        return "entry";
    }

    @GetMapping("/blog/add")
    private String blogAdd(Model model) {
        model.addAttribute("entry", new Entry());
        return "blogAdd";
    }

    @PostMapping("/blog/add")
    private String blogAdd(@ModelAttribute Entry entry) {
        entry.setAuthor(userService.getCurrentUser());
        entry.setTimestamp(new Date());
        blogService.addNewEntry(entry);
        return "redirect:/blog/entry/" + entry.getId();
    }

    @ResponseBody
    @PostMapping(value = "/blog/add/preview")
    private Entry blogAddPreview(@ModelAttribute Entry entry) {
        return blogService.preparePreview(entry);
    }

}
