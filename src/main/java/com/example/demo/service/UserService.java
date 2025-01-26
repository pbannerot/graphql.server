package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.model.Country;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.model.dto.LocationDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final LocationRepository locationRepository;

	public UserService(UserRepository userRepository, LocationRepository locationRepository) {
		this.userRepository = userRepository;
		this.locationRepository = locationRepository;
	}

	private UserDTO convertToUserDTO(User user) {
		LocationDTO locationDTO = new LocationDTO(user.getLocation().getId(), user.getLocation().getCity(),
				user.getLocation().getCountry());
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), locationDTO);
	}

	public List<UserDTO> users() {
		return userRepository.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
	}

	public Optional<UserDTO> getUser(UUID uuid) {
		return userRepository.findById(uuid).map(this::convertToUserDTO);
	}

	public UserDTO createUser(String firstName, String lastName, LocationDTO location) {
		Location getOrCreateLocation = locationRepository.findByCityAndCountry(location.city(), location.country())
				.orElseGet(() -> createLocationForCountry(location.country()));
		User user = new User.Builder().setFirstName(firstName).setLastName(lastName).setLocation(getOrCreateLocation).build();
		User savedUser = userRepository.save(user);
		return convertToUserDTO(savedUser);
	}

	public boolean deleteUser(UUID uuid) {
		boolean isDeleted = false;
		if (userRepository.findById(uuid).isPresent()) {
			userRepository.deleteById(uuid);
			isDeleted = true;
		}
		return isDeleted;
	}

	public List<UserDTO> getUsersByCountry(Country country) {
		return userRepository.findByLocation_Country(country).stream().map(this::convertToUserDTO)
				.collect(Collectors.toList());
	}

	public UserDTO assignLocationToUser(UUID userId, Country country) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new IllegalArgumentException("User not found");
		}
		
		Location location = locationRepository.findByCountry(country)
				.orElseGet(() -> createLocationForCountry(country));
		
		User user = userOptional.get();
		user.setLocation(location);
		
		return convertToUserDTO(userRepository.save(user));
	}

	private Location createLocationForCountry(Country country) {
		Location location = new Location.Builder().setCity("Default City").setCountry(country).build();
		return locationRepository.save(location);
	}
}
