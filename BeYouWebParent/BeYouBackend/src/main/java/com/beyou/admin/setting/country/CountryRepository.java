package com.beyou.admin.setting.country;

import java.util.List;

import com.beyou.common.entity.Country;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<Country,Integer>  {
    public List<Country> findAllByOrderByNameAsc();
}
