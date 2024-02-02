package application.weatherMeasurement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    @SequenceGenerator(name = "seq1", sequenceName = "seq1", allocationSize = 1)
    private Long id;

    @NotNull
    @Min(-100)
    @Max(100)
    private Float value;

    @NotNull
    private Boolean isRaining;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timestamp;
    @ManyToOne
    @NotNull
    @JoinColumn(referencedColumnName = "id", name = "sensor_id")
    private Sensor sensor;
}
