/**
 * CREATE Script for init of DB
 */

-- Create 3 cars

insert into car (id, license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values (1, 'L-CS8877E', 'BMW', 'BMW', 'Hamburg', 'AVAILABLE', now(), now());

insert into car (id, license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values (2, 'L-CS9977R', 'BMW', 'BMW', 'Berlin', 'IN_MAINTENANCE', now(), now());

insert into car (id, license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values (3, 'L-CS8347F', 'Mercedes', 'Daimler Motors Corporation', 'Hamburg', 'OUT_OF_SERVICE', now(), now());

insert into car (id, license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values (4, 'L-DS9345H', 'Mercedes', 'Daimler Motors Corporation', 'Hamburg', 'OUT_OF_SERVICE', now(), now());
