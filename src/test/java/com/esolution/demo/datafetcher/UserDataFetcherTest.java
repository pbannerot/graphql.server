package com.esolution.demo.datafetcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.esolution.demo.model.Country;
import com.esolution.demo.model.Location;
import com.esolution.demo.model.User;
import com.esolution.demo.model.dto.UserDTO;
import com.esolution.demo.repository.LocationRepository;
import com.esolution.demo.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserDataFetcherTest {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserDataFetcher usersDataFetcher;
    
    private User user1;
    private User user2;

	@BeforeEach
	void setUp() throws Exception {
		Location location1 = locationRepository.save(new Location.Builder().setCity("Paris").setCountry(Country.FR).build());
		Location location2 = locationRepository.save(new Location.Builder().setCity("London").setCountry(Country.UK).build());

		user1 = userRepository.save(new User.Builder().setFirstName("Alice").setLastName("Dupont").setLocation(location1).build());
        user2 = userRepository.save(new User.Builder().setFirstName("Bob").setLastName("Martin").setLocation(location2).build());
	}

	@Test
    void testUsers() {
		List<UserDTO> users = usersDataFetcher.users(null); 
		
		assertNotNull(users);
        assertTrue(users.size() > 0);
        assertEquals(user1.getFirstName(), users.get(0).firstName());
        assertEquals(user2.getLastName(), users.get(1).lastName());
    }

    @Test
    void testUserById() {
    	UserDTO result = usersDataFetcher.userById(user1.getId());
    	
    	assertNotNull(result);
        assertEquals(user1.getId(), result.id());
        assertEquals("Alice", result.firstName());
    }

    @Test
    void testUserByIdNotFound() {
    	UUID wrongId = UUID.randomUUID();
    	assertThrows(NoSuchElementException.class, () -> usersDataFetcher.userById(wrongId));
    } 

}
