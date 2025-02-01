package com.esolution.demo.service;

import org.springframework.stereotype.Service;

import com.esolution.demo.model.Location;
import com.esolution.demo.model.dto.LocationDTO;

@Service
public class LocationService {
	public LocationDTO convertToULocationDTO(Location location) {
		return new LocationDTO(location.getId(), location.getCity(), location.getCountry());
	}
}
