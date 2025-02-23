package com.esolution.graphql.server.model.dto;

import java.util.UUID;

import com.esolution.graphql.server.model.Country;

public record LocationDTO(UUID id, String city, Country country) {

}
