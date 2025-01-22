INSERT INTO hotel (name, photo)
VALUES ('First World Hotel & Plaza', 'first-world-plaza.jpg'),
       ('Flamingo Las Vegas', 'Flamingo Las Vegas Hotel & Casino.jpg'),
       ('Atlantis Paradise Island', 'Atlantis Paradise Island.jpg'),
       ('Hilton Hawaiian Village', 'HILTON HAWAIIAN VILLAGE.jpg'),
       ('Disneys Port Orleans Resort', 'Disney''s Port Orleans Resort2.png');

INSERT INTO users (firstname, lastname, username, password, role, phone, birth_date, money)
VALUES ('Vasya', 'Vasilyev', 'vasya@gmail.com', 112233, 'USER', '+375291478523', '1995-02-05',
        '2500'),
       ('Vanya', 'Ivanov', 'vanya@gmail.com', 112233, 'USER', '+375446842935', '1997-06-11', '3000'),
       ('Petr', 'Petrov', 'petrov@gmail.com', 112233, 'ADMIN', '+375291236547', '2000-11-09', '5000');

INSERT INTO room (occupancy, class, photo, price_per_day, hotel_id)
VALUES (3, 'BUSINESS', '720x720-superior-family-room.jpg', 89, 3),
       (4, 'BUSINESS', '11FWH-Deluxe-room-1400.jpg', 69, 1),
       (2, 'ECONOMY', '720x720-cf-superior-room.jpg', 39, 2),
       (3, 'COMFORT', '11FWH-Superior-deluxe-1400.jpg', 59, 2),
       (4, 'BUSINESS', 'GSW-hotel-Quads1-tile.jpg', 79, 2),
       (3, 'COMFORT', '2-720x720.jpg', 69, 3);

INSERT INTO room_order (user_id, room_id, status, payment_status, check_in_date, check_out_date)
VALUES (1, 1, 'APPROVED', 'APPROVED', '2024-10-15', '2024-10-25'),
       (2, 5, 'APPROVED', 'APPROVED', '2024-11-10', '2024-11-17'),
       (3, 6, 'APPROVED', 'APPROVED', '2024-11-20', '2024-11-30'),
       (1, 2, 'APPROVED', 'APPROVED', '2024-10-11', '2024-10-15');