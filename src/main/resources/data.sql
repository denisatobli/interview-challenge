/**
 * CREATE Script for init of DB
 */

-- Create 4 cars

insert into car (license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values ('L-CS8877E', 'BMW', 'BMW', 'Hamburg', 'AVAILABLE', now(), now());

insert into car (license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values ('L-CS9977R', 'BMW', 'BMW', 'Berlin', 'IN_MAINTENANCE', now(), now());

insert into car (license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values ('L-CS8347F', 'Mercedes', 'Daimler Motors Corporation', 'Hamburg', 'OUT_OF_SERVICE', now(), now());

insert into car (license_plate, brand, manufacturer, operation_city, status, created_at, last_updated_at)
    values ('L-DS9345H', 'Mercedes', 'Daimler Motors Corporation', 'Hamburg', 'OUT_OF_SERVICE', now(), now());
