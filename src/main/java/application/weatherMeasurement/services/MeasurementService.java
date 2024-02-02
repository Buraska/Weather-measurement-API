package application.weatherMeasurement.services;

import application.weatherMeasurement.models.Measurement;
import application.weatherMeasurement.repositories.MeasurementRepository;
import application.weatherMeasurement.utils.exceptions.MeasurementCannotBeCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MeasurementService {

    private final MeasurementRepository repository;

    private final SensorService sensorService;

    public MeasurementService(MeasurementRepository repository, SensorService sensorService) {
        this.repository = repository;
        this.sensorService = sensorService;
    }

    public void save(Measurement measurement){
        var sensor = sensorService.findByName(measurement.getSensor().getName())
                .orElseThrow(() -> new MeasurementCannotBeCreated("Cannot create that measurement " + measurement + ". That sensor does not exist."));
        measurement.setSensor(sensor);
        measurement.setTimestamp(new Timestamp(System.currentTimeMillis()));
        repository.save(measurement);
    };

    public List<Measurement> findAll(){
        return repository.findAll();
    }

    public Integer getRainyDaysCount(){
        return repository.findByIsRainingTrue().size();
    }
}
