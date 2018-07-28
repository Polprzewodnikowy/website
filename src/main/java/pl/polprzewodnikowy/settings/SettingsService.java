package pl.polprzewodnikowy.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SettingsService {

    @Autowired
    private SettingRepository settingRepository;

    public String getWebsiteTitle() {
        if (settingRepository.existsById("websiteTitle")) {
            return settingRepository.findById("websiteTitle").get().getValue();
        }
        return "Website";
    }

    public void addWebsiteSettingsToModel(Model model) {
        model.addAttribute("title", getWebsiteTitle());
    }

}
