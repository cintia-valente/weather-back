package com.template.presentation.controller;

import com.template.business.services.WeatherDataService;
import com.template.data.entity.WeatherDataEntity;
import com.template.dto.WeatherDataRequestDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4767")
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherDataController {

    final WeatherDataService weatherDataService;

    public WeatherDataController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WeatherDataRequestDTO> post(@RequestBody WeatherDataRequestDTO weatherDataRequestDTO) throws IOException {
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();

        BeanUtils.copyProperties(weatherDataRequestDTO, weatherDataEntity);
        WeatherDataEntity save = weatherDataService.save(weatherDataEntity);
        weatherDataRequestDTO.setIdWeatherData(save.getIdWeatherData());

        return ResponseEntity.status(HttpStatus.CREATED).body(weatherDataRequestDTO);
    }

    @ApiResponse(description = "lista registros de todas as cidades quando NÃO pesquisar a cidade.")
    @GetMapping("/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WeatherDataEntity>> getAll() throws IOException {
        var allWeatherData = weatherDataService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allWeatherData);
    }

    @ApiResponse(description = "lista todos os registros de uma cidade quando PESQUISAR a cidade.")
    @GetMapping("/{cityName}/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WeatherDataEntity>> getAllBy(@PathVariable String cityName) throws IOException {
        var allWeatherData = weatherDataService.findAllByName(cityName, Sort.by("date").descending());

        return ResponseEntity.status(HttpStatus.OK).body(allWeatherData);
    }

    @ApiResponse(description = "lista o registro do dia atual mais 6 dias consecutivos.")
    @GetMapping("/{cityName}/list-all-week")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WeatherDataEntity>> getBy(@PathVariable String cityName) throws IOException {
        List<WeatherDataEntity> wheaterDataList = weatherDataService.findByDateBetween(cityName);

        return new ResponseEntity<>(wheaterDataList, HttpStatus.OK);
    }

    @ApiResponse(description = "lista 10 registros por página quando PESQUISAR uma cidade.")
    @GetMapping("/{cityName}/list-all-page")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<WeatherDataEntity>> get(@PathVariable String cityName) throws IOException {
        Page<WeatherDataEntity> pageResult = weatherDataService.findAllPageByName(cityName);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @ApiResponse(description = "lista 10 registros por página, quando NÃO pesquisar uma cidade.")
    @GetMapping("/list-all-page")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<WeatherDataEntity>> get() throws IOException {
        Page<WeatherDataEntity> pageResult = weatherDataService.findAllPage();

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PutMapping("/{idWheaterData}")
    public ResponseEntity<WeatherDataRequestDTO> put(@PathVariable(value = "idWheaterData") Long idWheaterData, @RequestBody WeatherDataRequestDTO weatherDataRequestDTO) throws IOException{
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();

        BeanUtils.copyProperties(weatherDataRequestDTO, weatherDataEntity);
        WeatherDataEntity updateWeatherDataEntity = weatherDataService.update(idWheaterData, weatherDataEntity);
        weatherDataRequestDTO.setIdWeatherData(updateWeatherDataEntity.getIdWeatherData());

        return ResponseEntity.ok(weatherDataRequestDTO);
    }

    @DeleteMapping("/{idWheaterData}")
    public ResponseEntity<Void> delete(@PathVariable(value = "idWheaterData") Long idWheaterData) throws IOException{
        weatherDataService.deleteById(idWheaterData);
        return ResponseEntity.noContent().build();
    }
}
