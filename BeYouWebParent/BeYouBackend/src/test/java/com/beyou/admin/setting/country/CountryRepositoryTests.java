package com.beyou.admin.setting.country;


import java.util.List;
import java.util.Optional;

import com.beyou.common.entity.Country;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {
    
    @Autowired 
    private CountryRepository repo;

    @Test
    public void testCreateCountry(){
        // Country country1 = repo.save(new Country("Republic of India","IN"));
        Country country2 = repo.save(new Country("United States","US"));
        Country country3 = repo.save(new Country("United Kingdom","UK"));
        Country country4 = repo.save(new Country("China","CN"));
    }

    @Test
    public void testListCountries(){
        List<Country> listCountries  = repo.findAllByOrderByNameAsc();
        listCountries.forEach(System.out :: println);
    }

    @Test
    public void testUpdateCountry(){
        Integer id = 1;
        String name = "Republic of India";

        Country country = repo.findById(id).get();
        country.setName(name);

        Country updatedCountry = repo.save(country);
    }

    @Test
    public void testGetCountry(){
        Integer id = 1;
        Country country = repo.findById(id).get();

    }

    @Test
    public void testDeleteCountry(){
        Integer id = 1;
        repo.deleteById(id);

        Optional<Country> findById = repo.findById(id);
    }
}
