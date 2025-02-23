package com.esolution.graphql.server.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.dto.UserDTO;
import com.esolution.graphql.server.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

//http://localhost:9090/swagger-ui/index.html

@RestController
@RequestMapping("/demo")
public class UserController {
	@Autowired
	private UserService userService;
	
//	curl -X GET "http://localhost:9090/demo/users"
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.users());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") String userId) throws Exception {
		Optional<UserDTO> o = userService.getUser(UUID.fromString(userId));
		return o.isPresent() ? ResponseEntity.ok().body(o.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/users/country/{country}")
//	curl -X GET "http://localhost:9090/demo/users/country/CA" -w "%{http_code}\n" 
    public ResponseEntity<List<UserDTO>> getUsersByCountry(@PathVariable Country country) {
		List<UserDTO> usersInCountry = userService.getUsersByCountry(country);
        return usersInCountry.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usersInCountry);
    }

	@PostMapping("/users")
//	curl -X POST "http://localhost:9090/demo/users" \
//	-H "Content-Type: application/json" \
//	-d '{
//	    "firstName": "Alice",
//	    "lastName": "Dupont",
//	    "location": {
//	        "city": "Paris",
//	        "country": "FR"
//	    }
//	}'
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
		UserDTO createdUser = userService.createUser(user.firstName(), user.lastName(), user.location());
		return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(createdUser);
	}
	
	@PutMapping("/users/{userId}/location")
	// curl -X PUT "http://localhost:9090/demo/users/123e4567-e89b-12d3-a456-426614174000/location?country=FR" -H "Content-Type: application/json"
	public ResponseEntity<UserDTO> assignLocationToUser(@PathVariable UUID userId, @RequestParam Country country) {
		try {
			return ResponseEntity.ok(userService.assignLocationToUser(userId, country));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/users/{id}")
	// curl -X DELETE "http://localhost:9090/demo/users/123e4567-e89b-12d3-a456-426614174000"
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") String uuid) {
	    return userService.deleteUser(UUID.fromString(uuid)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
    @PutMapping("/users/{id}")
    @Operation(summary = "Mise à jour d'un utilisateur")
//    curl -X PUT http://localhost:8080/users/{id} \
//        -H "Content-Type: application/json" \
//        -d '{
//              "firstName": "Jane",
//              "lastName": "Smith",
//              "locationId": "e7e1f9f7-b3b3-4f0e-85e7-b3b34f0e85e7"
//            }'
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

}
