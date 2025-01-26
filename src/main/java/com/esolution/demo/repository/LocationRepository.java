package com.esolution.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esolution.demo.model.Country;
import com.esolution.demo.model.Location;

public interface LocationRepository extends JpaRepository<Location, UUID>{

	Optional<Location> findByCountry(Country country);
	Optional<Location> findByCityAndCountry(String city, Country country);

}
