package application.weatherMeasurement.repositories;

import application.weatherMeasurement.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {


    Optional<Sensor> findByName(String name);
}
