-- #############################################################################################################
-- Crear BD
CREATE DATABASE swift_ticket;

-- Crear Entidades

-- Tablas Catálogo
CREATE TABLE user_states (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE sponsors (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  image VARCHAR(100) NOT NULL
);

CREATE TABLE organizers (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE event_states (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE avatars (
  id SERIAL PRIMARY KEY,
  image VARCHAR(100) NOT NULL
);

CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE places (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  address VARCHAR(50) NOT NULL
);

-- Tablas principales
CREATE TABLE events (
  id SERIAL PRIMARY KEY,
  category_id INT NOT NULL,
  organizer_id INT NOT NULL,
  state_id INT NOT NULL,
  place_id INT NOT NULL,
  title VARCHAR(50) NOT NULL,
  duration VARCHAR(50) NOT NULL,
  date_time DATE NOT NULL,
  image VARCHAR(100) NOT NULL,
  FOREIGN KEY (category_id) REFERENCES categories (id),
  FOREIGN KEY (organizer_id) REFERENCES organizers (id),
  FOREIGN KEY (state_id) REFERENCES event_states (id),
  FOREIGN KEY (place_id) REFERENCES places (id)
);

CREATE TABLE tiers (
  id SERIAL PRIMARY KEY,
  event_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  capacity INT NOT NULL,
  price DECIMAL(6,2) NOT NULL,
  FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  role_id INT NOT NULL,
  state_id INT NOT NULL,
  avatar_id INT,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES roles (id),
  FOREIGN KEY (state_id) REFERENCES user_states (id),
  FOREIGN KEY (avatar_id) REFERENCES avatars (id)
);

CREATE TABLE tickets (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  tier_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (tier_id) REFERENCES tiers (id)
);

CREATE TABLE transactions (
  id SERIAL PRIMARY KEY,
  from_id INT NOT NULL,
  to_id INT NOT NULL,
  ticket_id INT NOT NULL,
  req_at TIMESTAMP NOT NULL,
  req_expires_at TIMESTAMP NOT NULL,
  accept_at TIMESTAMP NOT NULL,
  accept_expires_at TIMESTAMP NOT NULL,
  finished_at TIMESTAMP NOT NULL,
  FOREIGN KEY (from_id) REFERENCES users (id),
  FOREIGN KEY (to_id) REFERENCES users (id),
  FOREIGN KEY (ticket_id) REFERENCES tickets (id)
);

CREATE TABLE tokens (
  id SERIAL PRIMARY KEY,
  ticket_id INT NOT NULL,
  verified_at TIMESTAMP NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (ticket_id) REFERENCES tickets (id)
);

-- Tablas relación N:N
CREATE TABLE event_x_validator (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  event_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE event_x_sponsor (
  id SERIAL PRIMARY KEY,
  event_id INT NOT NULL,
  sponsor_id INT NOT NULL,
  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (sponsor_id) REFERENCES sponsors (id)
);

CREATE TABLE role_x_users (
  id SERIAL PRIMARY KEY,
  role_id INT NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);
