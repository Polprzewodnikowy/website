package pl.polprzewodnikowy.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class HomeController {

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("websiteTitle", "Test website");
        return "index";
    }

}
