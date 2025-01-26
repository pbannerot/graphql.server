package com.esolution.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esolution.demo.model.Country;
import com.esolution.demo.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	List<User> findByLocation_Country(Country country);
}
