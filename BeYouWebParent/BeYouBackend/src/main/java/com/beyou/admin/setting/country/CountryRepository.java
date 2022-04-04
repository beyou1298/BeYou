package com.beyou.admin.setting.country;

import java.util.List;

import com.beyou.common.entity.Country;

import org.springframework.data.repository.CrudRepository;


public interface CountryRepository extends CrudRepository<Country, Integer> {
	public List<Country> findAllByOrderByNameAsc();
}