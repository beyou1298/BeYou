package com.beyou.shipping;

import com.beyou.common.entity.Country;
import com.beyou.common.entity.ShippingRate;

import org.springframework.data.repository.CrudRepository;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
    
    public ShippingRate findByCountryAndState(Country country, String state);
}
