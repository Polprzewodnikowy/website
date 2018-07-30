package pl.polprzewodnikowy.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SettingsService {

    @Autowired
    private SettingRepository settingRepository;

    public void setWebsiteTitle(String title) {
        setValue("websiteTitle", title);
    }

    public void setGithubLink(String link) {
        setValue("githubLink", link);
    }

    public void setLinkedinLink(String link) {
        setValue("linkedinLink", link);
    }

    public void addWebsiteSettingsToModel(Model model) {
        model.addAttribute("websiteTitle", getSetting("websiteTitle"));
        model.addAttribute("githubLink", getSetting("githubLink"));
        model.addAttribute("linkedinLink", getSetting("linkedinLink"));
    }

    private Setting getSetting(String id) {
        if (settingRepository.existsById(id)) {
            return settingRepository.findById(id).get();
        }
        return null;
    }

    private void setSetting(Setting setting) {
        settingRepository.save(setting);
    }

    private String getValue(String id) {
        if (settingRepository.existsById(id)) {
            return settingRepository.findById(id).get().getValue();
        }
        return null;
    }

    private void setValue(String id, String value) {
        if (settingRepository.existsById(id)) {
            Setting setting = settingRepository.findById(id).get();
            setting.setValue(value);
            settingRepository.save(setting);
        } else {
            settingRepository.save(new Setting(id, value));
        }
    }

}
