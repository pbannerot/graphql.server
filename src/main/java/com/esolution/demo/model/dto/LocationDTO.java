package com.esolution.demo.model.dto;

import java.util.UUID;

import com.esolution.demo.model.Country;

public record LocationDTO(UUID id, String city, Country country) {

}
