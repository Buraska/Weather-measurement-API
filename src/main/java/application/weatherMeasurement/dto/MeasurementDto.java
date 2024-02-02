package application.weatherMeasurement.dto;

import application.weatherMeasurement.models.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDto {

    @NotNull
    @Min(-100)
    @Max(100)
    private Float value;

    @NotNull
    private Boolean isRaining;

    @NotNull
    private SensorDto sensor;
}
