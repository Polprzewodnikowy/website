package pl.polprzewodnikowy.settings;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SettingsService {

    public String getWebsiteTitle() {
        return "Website";
    }

    public void addWebsiteSettingsToModel(Model model) {
        model.addAttribute("title", getWebsiteTitle());
    }

}
