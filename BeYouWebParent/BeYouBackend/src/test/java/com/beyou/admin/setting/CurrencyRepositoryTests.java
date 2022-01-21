package com.beyou.admin.setting;

import java.util.Arrays;
import java.util.List;

import com.beyou.common.entity.Currency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class CurrencyRepositoryTests {

    @Autowired
    private CurrencyRepository repo;

    @Test
    public void testCreateCurrencies(){
        List<Currency> listCurrencies = Arrays.asList(
            new Currency("United States Dollor", "$", "USD"),
            new Currency("British Pound", "£", "GBP"),
            new Currency("Japnese Yen", "¥", "JPY"),
            new Currency("Euro","€","EUR"),
            new Currency("Russian Ruble","₽","RUB"),
            new Currency("South Korean Won","₩","KRW"),
            new Currency("Chinese Yuan","¥","CNY"),
            new Currency("Brazillian Real","R$","BRL"),
            new Currency("Australian Dollor","$","AUD"),
            new Currency("Candian Dollor","$","CAD"),
            new Currency("Vietnamese","₫","VND"),
            new Currency("Indian Rupees","₹","INR")
        );
        
        repo.saveAll(listCurrencies);

        Iterable<Currency> iterable = repo.findAll();
    }

    @Test
    public void testListAllByOrderByNameAsc(){
        List<Currency> currencies = repo.findAllByOrderByNameAsc();

        currencies.forEach(System.out::println);
    }
}
