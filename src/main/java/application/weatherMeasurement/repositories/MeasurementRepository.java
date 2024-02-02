package application.weatherMeasurement.repositories;

import application.weatherMeasurement.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}
