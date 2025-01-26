CREATE TABLE IF NOT EXISTS locations (
    id UUID PRIMARY KEY,
    city VARCHAR(255),
    country VARCHAR(2)
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    location_id UUID,
    FOREIGN KEY (location_id) REFERENCES locations(id)
);

INSERT INTO locations (id, city, country) VALUES (UUID(), 'Paris', 'FR');
INSERT INTO locations (id, city, country) VALUES (UUID(), 'New York', 'US');
INSERT INTO locations (id, city, country) VALUES (UUID(), 'Toronto', 'CA');

INSERT INTO users (id, first_name, last_name, location_id) 
VALUES (UUID(), 'Alice', 'Dupont', (SELECT id FROM locations WHERE city = 'Paris' LIMIT 1));

INSERT INTO users (id, first_name, last_name, location_id) 
VALUES (UUID(), 'Bob', 'Smith', (SELECT id FROM locations WHERE city = 'New York' LIMIT 1));

INSERT INTO users (id, first_name, last_name, location_id) 
VALUES (UUID(), 'Charlie', 'Johnson', (SELECT id FROM locations WHERE city = 'Toronto' LIMIT 1));