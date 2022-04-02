package com.beyou.admin.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.beyou.admin.setting.SettingService;
import com.beyou.common.entity.setting.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ReportController {
	
	@Autowired private SettingService settingService;
	
	@GetMapping("/reports")
	public String viewSalesReportHome(HttpServletRequest request) {
		loadCurrencySetting(request);
		return "reports/reports";
	}
	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<Setting> currencySettings = settingService.getCurrencySettings();
		
		for (Setting setting : currencySettings) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}	
	}	
}