package com.esolution.graphql.server.mutation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.esolution.graphql.server.graphql.controller.UserMutation;
import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.Location;
import com.esolution.graphql.server.model.User;
import com.esolution.graphql.server.model.dto.LocationDTOInput;
import com.esolution.graphql.server.model.dto.UserDTO;
import com.esolution.graphql.server.repository.LocationRepository;
import com.esolution.graphql.server.repository.UserRepository;

@SpringBootTest
@Transactional
class UserMutationTest {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserMutation userMutation;
    
    private User user1;
//    private User user2;

	@BeforeEach
	void setUp() throws Exception {
		Location location1 = locationRepository.save(new Location.Builder().setCity("Paris").setCountry(Country.FR).build());
		Location location2 = locationRepository.save(new Location.Builder().setCity("London").setCountry(Country.UK).build());

		user1 = userRepository.save(new User.Builder().setFirstName("Alice").setLastName("Dupont").setLocation(location1).build());
//        user2 = userRepository.save(new User.Builder().setFirstName("Bob").setLastName("Martin").setLocation(location2).build());
	}

	@Test
	void testCreateUser() {
		LocationDTOInput locationDTOInput = new LocationDTOInput("Toronto", Country.CA);
		UserDTO result = userMutation.createUser("Charlie", "Johnson", locationDTOInput);
		
		assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Charlie", result.firstName());
	}

	@Test
	void testDeleteUser() {
		boolean result = userMutation.deleteUser(user1.getId().toString());
		
		assertTrue(result);
	}

}
