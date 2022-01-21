package com.beyou.admin.setting;

import java.util.List;

import com.beyou.common.entity.setting.Setting;
import com.beyou.common.entity.setting.SettingBag;

public class GeneralSettingBag extends SettingBag {

    public GeneralSettingBag(List<Setting> listSetting) {
        super(listSetting);
    }

    public void updateCurrencySymbol(String value){
        super.update("CURRENCY_SYMBOL", value);
    }

    public void updateSitelogo(String value){
        super.update("SITE_LOGO", value);
    }
    
}
