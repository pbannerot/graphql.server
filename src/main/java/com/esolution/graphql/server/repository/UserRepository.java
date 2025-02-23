package com.esolution.graphql.server.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	List<User> findByLocation_Country(Country country);
}
