CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER,
    has_license BOOLEAN
    );

CREATE TABLE IF NOT EXISTS car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL
    );

CREATE TABLE IF NOT EXISTS person_car (
    person_id INTEGER,
    car_id INTEGER,
    PRIMARY KEY (person_id, car_id),
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (car_id) REFERENCES car(id)
);
