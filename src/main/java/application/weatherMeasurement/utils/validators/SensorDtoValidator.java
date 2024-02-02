package application.weatherMeasurement.utils.validators;

import application.weatherMeasurement.dto.SensorDto;
import application.weatherMeasurement.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SensorDtoValidator implements Validator {

    private SensorService service;


    @Autowired
    public SensorDtoValidator(SensorService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDto sensorDto = (SensorDto) target;

        if (service.isPresent(sensorDto.getName())){
            errors.rejectValue("name", "That name is already taken.");
        }
    }
}
