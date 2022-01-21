package com.beyou.setting;

import java.util.ArrayList;
import java.util.List;

import com.beyou.common.entity.Country;
import com.beyou.common.entity.State;
import com.beyou.common.entity.StateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class StateRestController {
    
    @Autowired
    private StateRepository repo;

    @GetMapping("/settings/list_states_by_countries/{id}")
    public List<StateDTO> listByCountry(@PathVariable("id") Integer countryId){
        List<State> listStates = repo.findByCountryOrderByNameAsc(new Country(countryId));
        List<StateDTO> result = new ArrayList<>();

        for(State state : listStates){
            result.add(new StateDTO(state.getId(), state.getName()));
        }

        return result;
    }
}
