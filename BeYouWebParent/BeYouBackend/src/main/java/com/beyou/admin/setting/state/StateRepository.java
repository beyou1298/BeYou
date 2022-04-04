package com.beyou.admin.setting.state;

import java.util.List;

import com.beyou.common.entity.Country;
import com.beyou.common.entity.State;

import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
}
