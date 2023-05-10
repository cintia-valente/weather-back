package com.template.data.repository;

import com.template.data.entity.WeatherDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherDataRepository extends JpaRepository <WeatherDataEntity, Long> {

    List<WeatherDataEntity> findAllByOrderByDateDesc();
    List<WeatherDataEntity> findAllByCityNameIgnoreCase(String cityName, Sort sort);

    Page<WeatherDataEntity> findAllByCityNameIgnoreCase(String cityName, Pageable pageable);

    List<WeatherDataEntity> findByCityNameIgnoreCaseAndDateBetween(String cityName, LocalDate startDate, LocalDate endDate, Sort sort);

    void deleteById (Long idWheaterData);

}
