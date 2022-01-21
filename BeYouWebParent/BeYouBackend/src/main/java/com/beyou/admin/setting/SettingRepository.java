package com.beyou.admin.setting;

import java.util.List;

import com.beyou.common.entity.setting.Setting;
import com.beyou.common.entity.setting.SettingCategory;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface SettingRepository extends PagingAndSortingRepository<Setting, String>  {

    public List<Setting> findByCategory(SettingCategory category);
}
