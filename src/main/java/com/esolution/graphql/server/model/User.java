package com.esolution.graphql.server.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/* public record User(UUID id, String firstName, String lastName, Location location) {
    public User(String firstName, String lastName, Location location) {
        this(UUID.randomUUID(), firstName, lastName, location);
    }
} */

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

	public User() {
    }

    private User(Builder builder) {
    	this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.location = builder.location;
    }
    

	public UUID getId() {
		return id;
	} 
	
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
		this.location = location;
	}
    
    public static class Builder {
    	private UUID id;
        private String firstName;
        private String lastName;
        private Location location;
        
		public Builder setId(UUID id) {
			this.id = id;
			return this;
		}
		
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public User build() {
            return new User(this);
        }
        
        public User update(UUID id) {
            return new User(this.setId(id));
        }
    }
	
}
