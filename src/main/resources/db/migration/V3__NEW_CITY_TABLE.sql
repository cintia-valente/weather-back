DROP TABLE IF EXISTS city CASCADE;
DROP TABLE IF EXISTS wheaterdata CASCADE;
DROP TABLE IF EXISTS wheater_data CASCADE;

CREATE TABLE city (
    id_city BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE wheater_data (
    id_wheater_data BIGINT PRIMARY KEY,
    id_city BIGINT NOT NULL,
    date DATE,
    day_time_enum VARCHAR(20),
    night_time_enum VARCHAR(20),
    max_temperature INTEGER,
    min_temperature INTEGER,
    precipitation INTEGER,
    humidity INTEGER,
    wind_speed INTEGER,
    FOREIGN KEY (id_city) REFERENCES City(id_city)
);
