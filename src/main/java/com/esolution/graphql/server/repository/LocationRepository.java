package com.esolution.graphql.server.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.Location;

public interface LocationRepository extends JpaRepository<Location, UUID>{

	Optional<Location> findByCountry(Country country);
	Optional<Location> findByCityAndCountry(String city, Country country);

}
