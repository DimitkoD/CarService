INSERT IGNORE INTO positions(name) values
('sales assistant'),
('manager');

INSERT IGNORE INTO cars(status, price, vin) values
(false, 20, 'JH4DC4440RS004255'),
(false, 25, 'SALVT2BG1CH654491'),
(false, 40, '3D73Y3CL6BG585460'),
(false, 50, '3D7KU28C63G798405');

INSERT IGNORE INTO customers(full_name, customer_status) values
('Dimitar Dobrev', false),
('Ivan Ivanov', false),
('Georgi Petrov', false);

INSERT IGNORE INTO employees(full_name, position_id) values
('Daniel Petrov', 1) ,
('Dobri Dimitrov', 2),
('Radoslav Shushmanov', 1);
