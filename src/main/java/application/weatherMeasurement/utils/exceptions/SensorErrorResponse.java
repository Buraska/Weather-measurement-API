package application.weatherMeasurement.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class SensorErrorResponse {
    private String message;
    private Timestamp timestamp;
}
