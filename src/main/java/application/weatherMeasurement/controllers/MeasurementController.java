package application.weatherMeasurement.controllers;


import application.weatherMeasurement.dto.MeasurementDto;
import application.weatherMeasurement.models.Measurement;
import application.weatherMeasurement.services.MeasurementService;
import application.weatherMeasurement.utils.exceptions.MeasurementCannotBeCreated;
import application.weatherMeasurement.utils.responses.ErrorResponse;
import application.weatherMeasurement.utils.validators.MeasurementDtoValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("measurements")
public class MeasurementController {

    private final MeasurementService service;

    private final MeasurementDtoValidator validator;

    private final ModelMapper mapper;

    public MeasurementController(MeasurementService service, MeasurementDtoValidator validator, ModelMapper mapper) {
        this.service = service;
        this.validator = validator;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDto measurementDto, BindingResult bindingResult){
        validator.validate(measurementDto, bindingResult);

        if (bindingResult.hasErrors()){
            var errors = bindingResult.getAllErrors();
            throw new MeasurementCannotBeCreated(Arrays.toString(errors.toArray()));
        }

        service.save(mapper.map(measurementDto, Measurement.class));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDto> getAllMeasurement(){
        return service.findAll().stream().map((x) -> mapper.map(x, MeasurementDto.class)).toList();
    }

    @GetMapping("/rainy-days-count")
    public Integer getRainyDaysCount(){
        return service.getRainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementCannotBeCreated e){
        var response = new ErrorResponse(e.getMessage(), new Timestamp(System.currentTimeMillis()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
