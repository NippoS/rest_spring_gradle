insert into files (name, location, status) values
('File_1', 'https://test.ru', 'ACTIVE'),
('File_2', 'https://test.ru', 'ACTIVE'),
('File_3', 'https://test.ru', 'ACTIVE')
;
insert into events (created, status) values
('2021-10-29 17:25:16', 'ACTIVE'),
('2021-10-29 17:17:45', 'ACTIVE'),
('2021-10-29 17:18:00', 'ACTIVE'),
('2021-10-29 17:15:34', 'ACTIVE'),
('2021-10-29 17:19:45', 'ACTIVE')
;
insert into users (username, first_name, last_name, password, status) values
('Nippos','Max', 'Nemolyakin', '$2a$12$6ww.nWZmQerVabVZl7bgbeA6eshRW3EZi5XB5I1vuScro1ftn8zRO', 'ACTIVE'),
('Virs05', 'Ivan', 'Leonov', '$2a$12$6ww.nWZmQerVabVZl7bgbeA6eshRW3EZi5XB5I1vuScro1ftn8zRO', 'ACTIVE'),
('Kolan4', 'Petya', 'Ivanov', '$2a$12$6ww.nWZmQerVabVZl7bgbeA6eshRW3EZi5XB5I1vuScro1ftn8zRO', 'ACTIVE')
;
insert into users_roles(user_id, role_id) values
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(3, 1)
;
insert into roles (name) values
('ROLE_USER'),
('ROLE_MODERATOR'),
('ROLE_ADMIN')
;