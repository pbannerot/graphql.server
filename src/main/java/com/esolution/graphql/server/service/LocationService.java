package com.esolution.graphql.server.service;

import org.springframework.stereotype.Service;

import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.Location;
import com.esolution.graphql.server.model.dto.LocationDTO;

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
