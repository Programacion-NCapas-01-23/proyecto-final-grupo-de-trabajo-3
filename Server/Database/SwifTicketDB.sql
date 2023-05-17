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
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  category_id INT NOT NULL,
  organizer_id INT NOT NULL,
  state_id INT NOT NULL,
  place_id INT NOT NULL,
  title VARCHAR(50) NOT NULL,
  duration VARCHAR(50) NOT NULL,
  date_time DATE NOT NULL,
  image VARCHAR(100) NOT NULL,
  CONSTRAINT event_pk PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (organizer_id) REFERENCES organizers (id)  ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (state_id) REFERENCES event_states (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (place_id) REFERENCES places (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE tiers (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  event_id uuid NOT NULL,
  name VARCHAR(50) NOT NULL,
  capacity INT NOT NULL,
  price DECIMAL(6,2) NOT NULL,
  CONSTRAINT tier_pk PRIMARY KEY (id),
  FOREIGN KEY (event_id) REFERENCES events (id) ON UPDATE CASCADE
);

CREATE TABLE users (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  state_id INT NOT NULL,
  avatar_id INT,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT user_pk PRIMARY KEY (id),
  FOREIGN KEY (state_id) REFERENCES user_states (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (avatar_id) REFERENCES avatars (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE tickets (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  user_id uuid NOT NULL,
  tier_id uuid NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT ticket_pk PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE,
  FOREIGN KEY (tier_id) REFERENCES tiers (id) ON UPDATE CASCADE
);

CREATE TABLE transactions (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  from_id uuid,
  to_id uuid NOT NULL,
  ticket_id uuid,
  req_at TIMESTAMP NOT NULL,
  req_expires_at TIMESTAMP NOT NULL,
  accept_at TIMESTAMP,
  accept_expires_at TIMESTAMP,
  finished_at TIMESTAMP,
  CONSTRAINT transaction_pk PRIMARY KEY (id),
  FOREIGN KEY (from_id) REFERENCES users (id) ON UPDATE CASCADE,
  FOREIGN KEY (to_id) REFERENCES users (id) ON UPDATE CASCADE,
  FOREIGN KEY (ticket_id) REFERENCES tickets (id) ON UPDATE CASCADE
);

CREATE TABLE tokens (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  ticket_id uuid NOT NULL,
  verified_at TIMESTAMP,
  expires_at TIMESTAMP NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT token_pk PRIMARY KEY (id),
  FOREIGN KEY (ticket_id) REFERENCES tickets (id) ON UPDATE CASCADE
);

-- Tablas relación N:N
CREATE TABLE event_x_validator (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  user_id uuid NOT NULL,
  event_id uuid NOT NULL,
  CONSTRAINT event_x_validator_pk PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE,
  FOREIGN KEY (event_id) REFERENCES events (id) ON UPDATE CASCADE
);

CREATE TABLE event_x_sponsor (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  event_id uuid NOT NULL,
  sponsor_id INT NOT NULL,
  CONSTRAINT event_x_sponsor_pk PRIMARY KEY (id),
  FOREIGN KEY (event_id) REFERENCES events (id) ON UPDATE CASCADE,
  FOREIGN KEY (sponsor_id) REFERENCES sponsors (id) ON UPDATE CASCADE
);

CREATE TABLE role_x_users (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  role_id INT NOT NULL,
  user_id uuid NOT NULL,
  CONSTRAINT role_x_users_pk PRIMARY KEY (id),
  FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE
);
