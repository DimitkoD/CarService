INSERT IGNORE INTO positions(id, name) values
(1, 'sales assistant'),
(2, 'manager');

INSERT IGNORE INTO cars(carId, status, price, vin) values
(1, false, 20, 'JH4DC4440RS004255'),
(2, false, 25, 'SALVT2BG1CH654491'),
(3, false, 40, '3D73Y3CL6BG585460'),
(4, false, 50, '3D7KU28C63G798405');

INSERT IGNORE INTO customers(id, fullName, customerStatus) values
(1, 'Dimitar Dobrev', false),
(2, 'Ivan Ivanov', false),
(3, 'Georgi Petrov', false);

INSERT IGNORE INTO employees(id, fullName, positionId) values
(1, 'Daniel Petrov', 1) ,
(2, 'Dobri Dimitrov', 2),
(3, 'Radoslav Shushmanov', 1);
