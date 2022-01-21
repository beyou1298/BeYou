package com.beyou.setting;

import java.util.List;

import com.beyou.common.entity.setting.Setting;
import com.beyou.common.entity.setting.SettingBag;

public class PaymentSettingBag extends SettingBag {

	public PaymentSettingBag(List<Setting> listSettings) {
		super(listSettings);
	}

	public String getURL() {
		return "https://api-m.sandbox.paypal.com";
	}
	
	public String getClientID() {
		return super.getValue("PAYPAL_API_CLIENT_ID");
	}
	
	public String getClientSecret() {
		return super.getValue("PAYPAL_API_CLIENT_SECRET");
	}
}