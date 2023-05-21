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
VALUES (1, 1, 1, 1, 'Conferencia de Tecnología de Mayo', '2 horas', '2023-05-20', 'evento1.jpg');

-- tiers


-- users
INSERT INTO users (state_id, avatar_id, name, email, password)
VALUES (1, 1, 'Carlos Mercado', '00058820@uca.edu.sv', 'secret');

-- tickets


-- transactions


-- tokens


-- event_x_validator


-- event_x_sponsor


-- role_x_users


-- system_state
INSERT INTO system_state (id, state) VALUES (1, 1);

-- bill

