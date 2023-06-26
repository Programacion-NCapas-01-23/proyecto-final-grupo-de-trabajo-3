-- #############################################################################################################
-- Crear BD
-- CREATE DATABASE swift_ticket;

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
  duration int NOT NULL,
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

CREATE TABLE verify_account_tokens (
  code VARCHAR NOT NULL,
  user_id uuid NOT NULL,
  verified_at TIMESTAMP,
  expires_at TIMESTAMP NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT verify_account_tokens_pk PRIMARY KEY (code),
  FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE
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

-- Tablas auxiliares
CREATE TABLE system_state (
  id INT PRIMARY KEY,
  state INT NOT NULL DEFAULT 1
);

CREATE TABLE bill (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  ticket_id uuid NOT NULL,
  user_id uuid NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(6,2) NOT NULL
);

-- Tablas de autenticacion

CREATE TABLE auth_tokens (
	code uuid NOT NULL DEFAULT gen_random_uuid(),
	"content" varchar NOT NULL,
	active boolean NOT NULL DEFAULT true,
	"timestamp" timestamp without time zone NULL DEFAULT CURRENT_TIMESTAMP,
	user_code uuid NULL,
	CONSTRAINT auth_token_pk PRIMARY KEY (code),
	CONSTRAINT auth_token_fk FOREIGN KEY (user_code) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);


--------------------------------------------------------------------------------------------------------------

-- INSERTS FOR swifTicket DB
-- user_states
INSERT INTO user_states (name) VALUES ('Activo');
INSERT INTO user_states (name) VALUES ('Bloqueado');
INSERT INTO user_states (name) VALUES ('No-verificado');

-- categories
INSERT INTO categories (name) VALUES ('Tecnología');
INSERT INTO categories (name) VALUES ('Deportes');
INSERT INTO categories (name) VALUES ('Entretenimiento');
INSERT INTO categories (name) VALUES ('Música');
INSERT INTO categories (name) VALUES ('Arte');

-- sponsors
INSERT INTO sponsors (name, image) VALUES ('Samsung', 'samsung.png');
INSERT INTO sponsors (name, image) VALUES ('Coca-Cola', 'coca-cola.png');
INSERT INTO sponsors (name, image) VALUES ('Nike', 'nike.png');
INSERT INTO sponsors (name, image) VALUES ('Apple', 'apple.png');
INSERT INTO sponsors (name, image) VALUES ('Amazon', 'amazon.png');

-- organizers
INSERT INTO organizers (name) VALUES ('Super eventos Tecno S.A.');
INSERT INTO organizers (name) VALUES ('Deportes Extremos Xtreme');
INSERT INTO organizers (name) VALUES ('María González');
INSERT INTO organizers (name) VALUES ('Carlos Rodríguez');
INSERT INTO organizers (name) VALUES ('Ana López');

-- event-states
INSERT INTO event_states (name) VALUES ('Programado');
INSERT INTO event_states (name) VALUES ('Suspendido');
INSERT INTO event_states (name) VALUES ('Finalizado');

-- avatar
INSERT INTO avatars (image) VALUES ('avatar1.jpg');
INSERT INTO avatars (image) VALUES ('avatar2.jpg');
INSERT INTO avatars (image) VALUES ('avatar3.jpg');
INSERT INTO avatars (image) VALUES ('avatar4.jpg');
INSERT INTO avatars (image) VALUES ('avatar5.jpg');

-- roles
INSERT INTO roles (name) VALUES ('Administrador');
INSERT INTO roles (name) VALUES ('Usuario');
INSERT INTO roles (name) VALUES ('Moderador');
INSERT INTO roles (name) VALUES ('Colaborador');
INSERT INTO roles (name) VALUES ('Super-admin');

-- places
INSERT INTO places (name, address) VALUES ('Centro de Convenciones', 'Calle Principal 123');
INSERT INTO places (name, address) VALUES ('Estadio Municipal', 'Avenida Deportiva 456');
INSERT INTO places (name, address) VALUES ('Teatro Nacional', 'Calle de los Artistas 789');
INSERT INTO places (name, address) VALUES ('Centro Comercial Plaza', 'Avenida Comercial 321');
INSERT INTO places (name, address) VALUES ('Galería de Arte Moderno', 'Calle del Arte 654');

-- events
INSERT INTO events (category_id, organizer_id, state_id, place_id, title, duration, date_time, image)
VALUES (1, 1, 1, 1, 'Conferencia de Tecnología de Mayo', 2, '2023-05-20', 'evento1.jpg');

-- tiers


-- users
-- INSERT INTO users (state_id, avatar_id, name, email, password)
-- VALUES (1, 1, 'Carlos Mercado', '00058820@uca.edu.sv', 'secret');

-- tickets


-- transactions


-- tokens


-- event_x_validator


-- event_x_sponsor


-- role_x_users


-- system_state
INSERT INTO system_state (id, state) VALUES (1, 1);

-- bill
