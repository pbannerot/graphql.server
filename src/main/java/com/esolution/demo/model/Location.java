package com.esolution.demo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/* public record Location(UUID id, String city, Country country) {
    public Location(String city, Country country) {
        this(UUID.randomUUID(), city, country);
    }
} */

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String city;
    @Enumerated(EnumType.STRING)
    private Country country;

    public Location() {
    }


    private Location(Builder builder) {
        this.city = builder.city;
        this.country = builder.country;
    }

    
     public UUID getId() {
     return id;
     }
     

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }

    public static class Builder {
        private String city;
        private Country country;

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setCountry(Country country) {
            this.country = country;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }
}
