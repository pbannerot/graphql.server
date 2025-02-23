package com.esolution.graphql.server.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.Location;
import com.esolution.graphql.server.model.User;
import com.esolution.graphql.server.repository.LocationRepository;
import com.esolution.graphql.server.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationRepository locationRepository;

	private User user1;
	private User user2;

	@BeforeEach
	public void setUp() {
		Location location1 = new Location.Builder().setCity("Paris").setCountry(Country.FR).build();
		Location location2 = new Location.Builder().setCity("London").setCountry(Country.UK).build();

		user1 = new User.Builder().setFirstName("Alice").setLastName("Dupont").setLocation(location1).build();
		user2 = new User.Builder().setFirstName("Bob").setLastName("Martin").setLocation(location2).build();

		locationRepository.save(location1);
		locationRepository.save(location2);
		userRepository.save(user1);
		userRepository.save(user2);
	}

	@Test
	public void testGetAllUsers() throws Exception {
		mockMvc.perform(get("/demo/users")
                .contentType(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())  
       .andExpect(jsonPath("$").isArray())  
       .andExpect(jsonPath("$[0].firstName").value("Alice"))  
       .andExpect(jsonPath("$[0].lastName").value("Dupont"));
	}

	@Test
	public void testGetUserById() throws Exception {
		mockMvc.perform(get("/demo/users/{id}", user1.getId().toString())).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Alice"));
	}

	@Test
	public void testCreateUser() throws Exception {
		String jsonRequest = "{ \"firstName\": \"Charlie\", \"lastName\": \"Brown\", \"location\": { \"city\": \"Berlin\", \"country\": \"DE\" }}";

		mockMvc.perform(post("/demo/users").contentType("application/json").content(jsonRequest))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.firstName").value("Charlie"))
				.andExpect(jsonPath("$.lastName").value("Brown"));
	}

	@Test
	public void testAssignLocationToUser() throws Exception {
		mockMvc.perform(put("/demo/users/{userId}/location?country=FR", user1.getId().toString()))
				.andExpect(status().isOk()).andExpect(jsonPath("$.location.country").value("FR"));
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(delete("/demo/users/{id}", user1.getId().toString())).andExpect(status().isNoContent());
	}
	
	@Test
    public void testGetUsersByCountry() throws Exception {
        mockMvc.perform(get("/demo/users/country/FR")
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$[0].location.country").value("FR"));
    }

    @Test
    public void testGetUsersByCountryNotFound() throws Exception {
        mockMvc.perform(get("/demo/users/country/XX")
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}
