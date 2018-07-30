package pl.polprzewodnikowy.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SettingsService {

    @Autowired
    private SettingRepository settingRepository;

    public void addWebsiteSettingsToModel(Model model) {
        model.addAttribute("websiteTitle", getSetting("websiteTitle"));
        model.addAttribute("facebookLink", getSetting("facebookLink"));
        model.addAttribute("twitterLink", getSetting("twitterLink"));
        model.addAttribute("githubLink", getSetting("githubLink"));
        model.addAttribute("linkedinLink", getSetting("linkedinLink"));
        model.addAttribute("authorLore", getSetting("authorLore"));
    }

    public Setting getSetting(String id) {
        if (settingRepository.existsById(id)) {
            return settingRepository.findById(id).get();
        }
        return new Setting(id, null);
    }

    public void setSetting(Setting setting) {
        settingRepository.save(setting);
    }

}
