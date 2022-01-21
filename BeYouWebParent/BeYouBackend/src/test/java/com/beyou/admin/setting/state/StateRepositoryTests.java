package com.beyou.admin.setting.state;

import java.util.List;
import java.util.Optional;

import com.beyou.common.entity.Country;
import com.beyou.common.entity.State;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {
    
    @Autowired 
    private StateRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateStateInIndia(){
        Integer countryId = 1;
        Country country = entityManager.find(Country.class, countryId);

        State state = repo.save(new State("West Bengal",country));
    }

    @Test
    public void testCreateStateInUS(){
        Integer countryId = 2;
        Country country = entityManager.find(Country.class, countryId);

        State state = repo.save(new State("Washington",country));
    }

    @Test
    public void testListStateByCountry(){
        Integer countryId = 2;
        Country country = entityManager.find(Country.class, countryId);

        List<State> listStates = repo.findByCountryOrderByNameAsc(country);

        listStates.forEach(System.out :: println);
    }

    @Test
    public void testUpdateState(){

        Integer stateId = 1;
        String name = "Tamil Nadu";

        State state = repo.findById(stateId).get();
        state.setName(name);

        State updatedState = repo.save(state);
    }

    @Test
    public void testGetState(){
        Integer stateId = 3;
        Optional<State> findById = repo.findById(stateId);
    }

    @Test
    public void testDeleteState(){

        Integer stateId = 8;
        repo.deleteById(stateId);

        Optional<State> findById = repo.findById(stateId);
    }
}
