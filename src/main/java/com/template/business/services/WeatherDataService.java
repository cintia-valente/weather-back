package com.template.business.services;

import com.template.config.exceptions.NoContentException;
import com.template.data.entity.CityEntity;
import com.template.data.entity.WeatherDataEntity;
import com.template.data.repository.WeatherDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherDataService {

    final WeatherDataRepository weatherDataRepository;

    final CityService cityService;

    public WeatherDataService(WeatherDataRepository weatherDataRepository, CityService cityService) {
        this.weatherDataRepository = weatherDataRepository;
        this.cityService = cityService;
    }

    public WeatherDataEntity save(WeatherDataEntity weatherDataEntity) throws IOException {
        return weatherDataRepository.save(weatherDataEntity);
    }

    public List<WeatherDataEntity> findAll() throws IOException {
        return weatherDataRepository.findAllByOrderByDateDesc();
    }

    public List<WeatherDataEntity> findAllByName(String cityName, Sort sort) throws IOException {
        return weatherDataRepository.findAllByCityNameIgnoreCase(cityName, sort);
    }

    public List<WeatherDataEntity> findByDateBetween(String cityName) throws IOException {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        Sort sort = Sort.by("date").ascending();

        List<WeatherDataEntity> wheaterDataList = weatherDataRepository.findByCityNameIgnoreCaseAndDateBetween(cityName, startDate, endDate, sort);

        if (wheaterDataList.isEmpty()) {
            throw new NoContentException("No Content");
        }

        return wheaterDataList;
    }

    public Page<WeatherDataEntity> findAllPageByName(String cityName) throws IOException {
        Integer page = 0;
        Integer size = 10;
        Pageable pageableByCity = PageRequest.of(page, size, Sort.by("date").descending());

        Page<WeatherDataEntity> pageByCityResult = weatherDataRepository.findAllByCityNameIgnoreCase(cityName, pageableByCity);

        if (pageByCityResult.isEmpty()) {
            throw new NoContentException("No Content");
        }

        return pageByCityResult;
    }

    public Page<WeatherDataEntity> findAllPage() throws IOException {
        Integer page = 0;
        Integer size = 10;
        Pageable pageableAll = PageRequest.of(page, size, Sort.by("date").descending());

        Page<WeatherDataEntity> pageAllResult = weatherDataRepository.findAll(pageableAll);

        if (pageAllResult.isEmpty()) {
            throw new NoContentException("No Content");
        }

        return pageAllResult;
    }

    public WeatherDataEntity update(Long idWheaterData, WeatherDataEntity weatherDataEntity) throws IOException{
        Optional<WeatherDataEntity> wheaterDataEntityOptional = weatherDataRepository.findById(idWheaterData);
        Optional<CityEntity> cityEntityOptional = cityService.findById(wheaterDataEntityOptional.get().getCity().getIdCity());

        cityEntityOptional.get().setName(weatherDataEntity.getCity().getName());
        CityEntity cityEntity = cityEntityOptional.get();

        WeatherDataEntity weatherDataEntityUpdate = wheaterDataEntityOptional.get();

        weatherDataEntityUpdate.setCity(cityEntity);
        weatherDataEntityUpdate.setDate(weatherDataEntity.getDate());
        weatherDataEntityUpdate.setDayTimeEnum(weatherDataEntity.getDayTimeEnum());
        weatherDataEntityUpdate.setNightTimeEnum(weatherDataEntity.getNightTimeEnum());
        weatherDataEntityUpdate.setMaxTemperature(weatherDataEntity.getMaxTemperature());
        weatherDataEntityUpdate.setMinTemperature(weatherDataEntity.getMinTemperature());
        weatherDataEntityUpdate.setPrecipitation(weatherDataEntity.getPrecipitation());
        weatherDataEntityUpdate.setHumidity(weatherDataEntity.getHumidity());
        weatherDataEntityUpdate.setWindSpeed(weatherDataEntity.getWindSpeed());

        return weatherDataRepository.save(weatherDataEntityUpdate);
    }

    public void deleteById(Long idWheaterData) throws IOException {
        weatherDataRepository.deleteById(idWheaterData);
    }
}
