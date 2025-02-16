package com.esolution.demo.service;

import org.springframework.stereotype.Service;

import com.esolution.demo.model.Country;
import com.esolution.demo.model.Location;
import com.esolution.demo.model.dto.LocationDTO;

@Service
public class LocationService {
	public LocationDTO convertToLocationDTO(Location location) {
		return new LocationDTO(location.getId(), location.getCity(), location.getCountry());
	}

	public LocationDTO findByCountry(Country ca) {
		// TODO Auto-generated method stub
		return null;
	}
}
