package com.esolution.demo.graphql;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.esolution.demo.model.Country;
import com.esolution.demo.model.Location;
import com.esolution.demo.model.dto.LocationDTO;
import com.esolution.demo.model.dto.LocationDTOInput;
import com.esolution.demo.model.dto.UserDTO;
import com.esolution.demo.repository.LocationRepository;
import com.esolution.demo.service.LocationService;
import com.esolution.demo.service.UserService;

@Controller
public class UserMutation {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@MutationMapping
	public UserDTO createUser(@Argument String firstName, @Argument String lastName, @Argument LocationDTOInput location) {
		Location newLocation = new Location.Builder()
	            .setCity(location.city())
	            .setCountry(location.country())
	            .build();
		LocationDTO locationDTO = locationService.convertToLocationDTO(newLocation);
		return userService.createUser(firstName, lastName, locationDTO);
	}
	
	@MutationMapping
    public Boolean deleteUser(@Argument String id) {
        return userService.deleteUser(UUID.fromString(id));
    }
	
	@MutationMapping
	public UserDTO createSimpleCAUser(@Argument String firstName, @Argument String lastName) {
		LocationDTO locationDTO = locationService.convertToLocationDTO(locationRepository.findByCountry(Country.CA).get());
		return userService.createUser(firstName, lastName, locationDTO);
	}
}
