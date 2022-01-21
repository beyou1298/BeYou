package com.beyou.admin.setting;

import java.util.List;
import java.util.Optional;

import com.beyou.common.entity.Currency;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CurrencyRepository extends PagingAndSortingRepository<Currency, String>  {
    
    public List<Currency> findAllByOrderByNameAsc();

    public Optional<Currency> findById(Integer currencyId);
}
