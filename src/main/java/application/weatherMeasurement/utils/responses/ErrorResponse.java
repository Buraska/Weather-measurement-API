package application.weatherMeasurement.utils.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Timestamp timestamp;
}
