package application.weatherMeasurement;

import application.weatherMeasurement.controllers.SensorController;
import application.weatherMeasurement.dto.SensorDto;
import application.weatherMeasurement.services.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import java.util.Arrays;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherMeasurementApplicationTests {

    @Autowired
    private SensorController sensorController;

    @Autowired
    private SensorService service;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(sensorController).isNotNull();
    }


    @Test
    void canRegisterSensor(){
        var sensorDto = new SensorDto("Vic-OZIM");
        var url = "http://localhost:" + port + "/sensors/registration";
        HttpEntity<SensorDto> request = new HttpEntity<>(sensorDto);


        String response = restTemplate.postForObject(url, request, String.class);

        System.out.println(Arrays.toString(service.findAll().toArray()));

    }
}
