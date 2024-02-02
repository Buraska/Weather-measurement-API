package application.weatherMeasurement.repositories;

import application.weatherMeasurement.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findByIsRainingTrue();
}
