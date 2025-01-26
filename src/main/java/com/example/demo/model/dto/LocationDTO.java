package com.example.demo.model.dto;

import java.util.UUID;

import com.example.demo.model.Country;

public record LocationDTO(UUID id, String city, Country country) {

}
