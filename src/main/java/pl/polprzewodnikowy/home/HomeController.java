package pl.polprzewodnikowy.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polprzewodnikowy.user.User;

@Controller
@RequestMapping("/")
class HomeController {

    @RequestMapping("")
    public String home(Model model) {
        User user = new User();
        user.setId(1);
        user.setName("TestUser");
        model.addAttribute("user", user);
        model.addAttribute("websiteTitle", "Test website");
        return "index";
    }

}
