package application.weatherMeasurement.utils.validators;

import application.weatherMeasurement.dto.MeasurementDto;
import application.weatherMeasurement.dto.SensorDto;
import application.weatherMeasurement.models.Measurement;
import application.weatherMeasurement.services.MeasurementService;
import application.weatherMeasurement.services.SensorService;
import application.weatherMeasurement.utils.exceptions.MeasurementCannotBeCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class MeasurementDtoValidator implements Validator {

    private final MeasurementService measurementService;

    private final SensorService sensorService;

    public MeasurementDtoValidator(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var measurement = (MeasurementDto) target;
        if (!sensorService.isPresent(measurement.getSensor().getName())){
            errors.rejectValue("sensor", "Sensor with that name does not exist.");
        };

    }
}
