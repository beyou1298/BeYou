package com.beyou.setting;

import java.util.List;

import com.beyou.common.entity.setting.Setting;
import com.beyou.common.entity.setting.SettingCategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SettingRepository extends PagingAndSortingRepository<Setting, String>  {

    public List<Setting> findByCategory(SettingCategory category);

    @Query("SELECT s FROM Setting s WHERE s.category = ?1 OR s.category = ?2")
    public List<Setting> findByTwoCategories(SettingCategory catOne, SettingCategory catTwo);

    public Setting findByKey(String key);
}
