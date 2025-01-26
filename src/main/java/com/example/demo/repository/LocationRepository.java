package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Country;
import com.example.demo.model.Location;

public interface LocationRepository extends JpaRepository<Location, UUID>{

	Optional<Location> findByCountry(Country country);
	Optional<Location> findByCityAndCountry(String city, Country country);

}
