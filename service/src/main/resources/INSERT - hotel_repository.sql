INSERT INTO hotel (name, photo)
VALUES ('First World Hotel & Plaza', 'photo1.jpg'),
       ('Flamingo Las Vegas', 'photo2.jpg'),
       ('Atlantis Paradise Island', 'photo3.jpg'),
       ('Hilton Hawaiian Village', 'photo4.jpg'),
       ('Disneys Port Orleans Resort', 'photo5.jpg');

INSERT INTO users (first_name, last_name, email, password, role, phone, photo, birth_date, money)
VALUES ('Vasya', 'Vasilyev', 'vasya@gmail.com', '112233', 'USER', '+375291478523', 'userphoto1.jpg', '1995-02-05', '2500'),
       ('Vanya', 'Ivanov', 'vanya@gmail.com', '223344', 'USER', '+375446842935', 'userphoto2.jpg', '1997-06-11', '3000'),
       ('Petr', 'Petrov', 'petrov@gmail.com', '123456', 'ADMIN', '+375291236547', 'userphoto3.jpg', '2000-11-09', '5000');

INSERT INTO room (occupancy, class, photo, price_per_day, hotel_id)
VALUES (2,'ECONOMY', 'roomphoto001.jpg', 29, 1),
       (3,'COMFORT', 'roomphoto002.jpg', 49, 1),
       (4,'BUSINESS', 'roomphoto003.jpg', 69, 1),
       (2,'ECONOMY', 'roomphoto004.jpg', 39, 2),
       (3,'COMFORT', 'roomphoto005.jpg', 59, 2),
       (4,'BUSINESS', 'roomphoto006.jpg', 79, 2);

INSERT INTO room_order (user_id, room_id, status, payment_status, check_in_date, check_out_date)
VALUES (14, 1, 'OPEN', 'APPROVED', '2024-10-15', '2024-10-25'),
       (15, 5, 'APPROVED', 'APPROVED', '2024-11-10', '2024-11-17'),
       (16, 6, 'APPROVED', 'APPROVED', '2024-11-20', '2024-11-30');