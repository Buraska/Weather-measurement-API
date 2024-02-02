package application.weatherMeasurement.controllers;

import application.weatherMeasurement.models.Sensor;
import application.weatherMeasurement.utils.SensorDtoValidator;
import application.weatherMeasurement.dto.SensorDto;
import application.weatherMeasurement.services.SensorService;
import application.weatherMeasurement.utils.exceptions.SensorCannotBeCreated;
import application.weatherMeasurement.utils.exceptions.SensorErrorResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;

@RestController
@RequestMapping("sensors")
public class SensorController {

    private final ModelMapper mapper;
    private final SensorService service;
    private final SensorDtoValidator validator;

    public SensorController(ModelMapper mapper, SensorService service, SensorDtoValidator validator) {
        this.mapper = mapper;
        this.service = service;
        this.validator = validator;
    }

    @PostMapping("/registration")
    public HttpEntity<HttpStatus> registration(@RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult){

        validator.validate(sensorDto, bindingResult);
        if (bindingResult.hasErrors()){
            var errors = bindingResult.getAllErrors();
            throw new SensorCannotBeCreated(Arrays.toString(errors.toArray()));
        }

        service.save(mapper.map(sensorDto, Sensor.class));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorCannotBeCreated e){
        var response = new SensorErrorResponse(e.getMessage(), new Timestamp(System.currentTimeMillis()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
