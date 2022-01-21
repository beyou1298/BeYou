package com.beyou.admin.setting;

import java.util.ArrayList;
import java.util.List;

import com.beyou.common.entity.setting.Setting;
import com.beyou.common.entity.setting.SettingCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    
    @Autowired
    private SettingRepository repo;

    public List<Setting> listAllSettings(){
        return (List<Setting>) repo.findAll();
    }

    public GeneralSettingBag getGeneralSettings(){
        List<Setting> settings = new ArrayList<>();

        List<Setting> generaSettings = repo.findByCategory(SettingCategory.GENERAL);
        List<Setting> currencySettings = repo.findByCategory(SettingCategory.CURRENCY);

        settings.addAll(generaSettings);
        settings.addAll(currencySettings);

        return new GeneralSettingBag(settings);
    }

    public void saveAll(Iterable<Setting> settings){
        repo.saveAll(settings);
    }

    public List<Setting> getMailServerSettings() {
		return repo.findByCategory(SettingCategory.MAIL_SERVER);
	}
	
	public List<Setting> getMailTemplateSettings() {
		return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
	}	

    public List<Setting> getCurrencySettings() {
		return repo.findByCategory(SettingCategory.CURRENCY);
	}

    public List<Setting> getPaymentSettings() {
		return repo.findByCategory(SettingCategory.PAYMENT);
	}
}
