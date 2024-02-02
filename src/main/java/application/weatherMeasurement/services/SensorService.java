package application.weatherMeasurement.services;

import application.weatherMeasurement.models.Sensor;
import application.weatherMeasurement.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SensorService {

    private final SensorRepository repository;

    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(Sensor sensor){
        repository.save(sensor);
    }

    public List<Sensor> findAll(){
        return repository.findAll();
    }

    public Optional<Sensor> findByName(String sensorName){
        return repository.findByName(sensorName);
    }
    public boolean isPresent(String sensorName){
        return repository.findByName(sensorName).isPresent();
    }
}
