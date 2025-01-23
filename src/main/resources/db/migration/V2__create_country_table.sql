CREATE TABLE IF NOT EXISTS countries
(
    id    uuid PRIMARY KEY,
    title varchar(255)
);

CREATE TABLE IF NOT EXISTS cities
(
    id         uuid PRIMARY KEY,
    title      varchar(255),
    country_id uuid,
    FOREIGN KEY (country_id) REFERENCES countries (id)
);