DROP TABLE IF EXISTS city CASCADE;
DROP TABLE IF EXISTS wheaterdata CASCADE;
DROP TABLE IF EXISTS wheater_data CASCADE;

CREATE TABLE city (
    id_city BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE weather_data (
    id_weather_data BIGSERIAL PRIMARY KEY,
    id_city BIGSERIAL NOT NULL,
    date DATE NOT NULL,
    day_time_enum VARCHAR(20) NOT NULL,
    night_time_enum VARCHAR(20) NOT NULL,
    max_temperature INTEGER NOT NULL,
    min_temperature INTEGER NOT NULL,
    precipitation INTEGER NOT NULL,
    humidity INTEGER NOT NULL,
    wind_speed INTEGER NOT NULL,
    FOREIGN KEY (id_city) REFERENCES City(id_city)
);
