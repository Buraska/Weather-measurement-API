package application.weatherMeasurement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    @SequenceGenerator(name = "seq1", sequenceName = "seq1", allocationSize = 1)
    private Long id;

    @NotNull
    private Float value;

    @NotNull
    private Boolean isRaining;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "sensor_id")
    private Sensor sensor;
}
