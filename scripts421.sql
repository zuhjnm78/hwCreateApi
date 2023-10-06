ALTER TABLE student ADD CONSTRAINT check_age CHECK (age >= 16),
ADD CONSTRAINT unique_name UNIQUE (name),
ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty ADD CONSTRAINT unique_name_color_pair UNIQUE (name, color);

CREATE TABLE IF NOT EXISTS student (id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER DEFAULT 20,
    faculty_id INTEGER,
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
    );