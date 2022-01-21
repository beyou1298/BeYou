package com.beyou.admin.setting.state;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.beyou.admin.setting.country.CountryRepository;
import com.beyou.common.entity.Country;
import com.beyou.common.entity.State;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class StateRestControllerTests {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CountryRepository countryRepo;

    @Autowired
    StateRepository stateRepo;

    @Test
    @WithMockUser(username = "nam", password = "something", roles = "Admin")
    public void testListByCountries() throws Exception{
        Integer countryId = 2;
        String url = "/states/list_by_country/" +countryId;

        MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        State[] states = objectMapper.readValue(jsonResponse, State[].class);
    }

    @Test
    @WithMockUser(username = "nam", password = "something", roles = "Admin")
    public void testCreateState() throws Exception{
        String url = "/states/save";
        Integer countryId = 2;
        Country country = countryRepo.findById(countryId).get();
        State state = new State("Arizona",country);


        MvcResult result = mockMvc.perform(post(url).contentType("application/json")
            .content(objectMapper.writeValueAsString(state))
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

        String response = result.getResponse().getContentAsString();
        Integer stateId = Integer.parseInt(response);

        Optional<State> findById = stateRepo.findById(stateId);
    }

    @Test
    @WithMockUser(username = "nam", password = "something", roles = "Admin")
    public void testUpdateState() throws Exception{
        String url = "/states/save";
        Integer stateId = 9;
        String stateName = "Alaska";

        State state = stateRepo.findById(stateId).get();
        state.setName(stateName);

        mockMvc.perform(post(url).contentType("application/json")
            .content(objectMapper.writeValueAsString(state))
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(stateId)));

        Optional<State> findById = stateRepo.findById(stateId);
        State updatedState = findById.get();
    }

    @Test
    @WithMockUser(username = "nam", password = "something", roles = "Admin")
    public void testDeleteState() throws Exception{
        Integer stateId = 5;
        String url = "/states/delete/" + stateId;
        mockMvc.perform(get(url)).andExpect(status().isOk());

        Optional<State> findById = stateRepo.findById(stateId);
    }
}
