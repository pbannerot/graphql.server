package com.esolution.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esolution.demo.model.Country;
import com.esolution.demo.model.Location;
import com.esolution.demo.model.User;
import com.esolution.demo.model.dto.LocationDTO;
import com.esolution.demo.model.dto.UserDTO;
import com.esolution.demo.repository.LocationRepository;
import com.esolution.demo.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	private final Sinks.Many<String> messageSink = Sinks.many().multicast().onBackpressureBuffer();
	private final Sinks.Many<UserDTO> userCreatedSink = Sinks.many().replay().all();
	private final Sinks.Many<UUID> userDeletedSink = Sinks.many().replay().all();
	private final Sinks.Many<UserDTO> userUpdatedSink = Sinks.many().replay().all();
	
	public Sinks.Many<String> getMessageSink() {
        return messageSink;
    }
	
	public Sinks.Many<UserDTO> getUserCreatedSink() {
		return userCreatedSink;
	}

	public Sinks.Many<UUID> getUserDeletedSink() {
		return userDeletedSink;
	}
	
	public Sinks.Many<UserDTO> getUserUpdatedSink() {
		return userUpdatedSink;
	}

	public UserDTO convertToUserDTO(User user) {
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

	@Transactional
	public UserDTO createUser(String firstName, String lastName, LocationDTO location) {
		Location getOrCreateLocation = locationRepository.findByCityAndCountry(location.city(), location.country())
				.orElseGet(() -> createLocationForCountry(location.city(), location.country()));
		User user = new User.Builder().setFirstName(firstName).setLastName(lastName).setLocation(getOrCreateLocation).build();
		User savedUser = userRepository.save(user);
		
		userCreatedSink.tryEmitNext(convertToUserDTO(savedUser));
		EmitResult result = messageSink.tryEmitNext("Create new user with id: " + savedUser.getId());
		if (result.isFailure()) {
			logger.error(result.name());
		}
		
		return convertToUserDTO(savedUser);
	}

	@Transactional
	public boolean deleteUser(UUID uuid) {
		boolean isDeleted = false;
		
		Optional<User> optional = userRepository.findById(uuid);
		if (optional.isPresent()) {
			User user = optional.get();
			userRepository.deleteById(uuid);
			isDeleted = true;
			
			userDeletedSink.tryEmitNext(uuid);
			messageSink.tryEmitNext("Delete user with id: " + user.getId());
		}
		return isDeleted;
	}

	public List<UserDTO> getUsersByCountry(Country country) {
		return userRepository.findByLocation_Country(country).stream().map(this::convertToUserDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public UserDTO assignLocationToUser(UUID userId, Country country) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new IllegalArgumentException("User not found");
		}
		User user = userOptional.get();
		
		Location location = locationRepository.findByCountry(country)
				.orElseGet(() -> createLocationForCountry(user.getLocation().getCity(), country));
		user.setLocation(location);
		
		return convertToUserDTO(userRepository.save(user));
	}

	@Transactional
	private Location createLocationForCountry(String city, Country country) {
		Location location = new Location.Builder().setCity(city).setCountry(country).build();
		return locationRepository.save(location);
	}

	@Transactional
	public UserDTO updateUser(UUID id, UserDTO user) {
		Optional <User> o = userRepository.findById(id);
		if (o.isPresent()) {
			Location l = o.get().getLocation();
			
			Location location = new Location.Builder()
					.setCity(l.getCity())
					.setCountry(l.getCountry())
					.build();
			location.setId(l.getId());
			
			User updatedUser = new User.Builder()
					.setFirstName(user.firstName())
					.setLastName(user.lastName())
					.setLocation(location)
					.build();
			
			updatedUser.setId(id);
			user = convertToUserDTO(userRepository.save(updatedUser));
			
			userUpdatedSink.tryEmitNext(user);
			messageSink.tryEmitNext("User with id: " + user.id() + " updated");
		} 
		return user;
	}
}
