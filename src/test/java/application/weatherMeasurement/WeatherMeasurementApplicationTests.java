package application.weatherMeasurement;

import application.weatherMeasurement.controllers.SensorController;
import application.weatherMeasurement.dto.MeasurementDto;
import application.weatherMeasurement.dto.SensorDto;
import application.weatherMeasurement.services.MeasurementService;
import application.weatherMeasurement.services.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class WeatherMeasurementApplicationTests {

    @Autowired
    private SensorController sensorController;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void contextLoads() {
        assertThat(sensorController).isNotNull();
    }


    @Test
    void canRegisterSensor() throws Exception {
        var sensorDto = new SensorDto("Vic-OZIM");
        postSensor(sensorDto)
                .andExpect(status().isOk());

        assertThat(sensorService.findAll().size()).isEqualTo(1);
    }

    @Test
    void twoSensorsWithEqualsNameThrowsError() throws Exception {
        var sensorDto = new SensorDto("Vic-OZIM");
        postSensor(sensorDto);

        postSensor(sensorDto)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Field error in object 'sensorDto' on field 'name'")));

        assertThat(sensorService.findAll().size()).isEqualTo(1);
    }

    @Test
    void canAddMeasurement() throws Exception {
        var sensorDto = new SensorDto("Vic-OZIM");
        postSensor(sensorDto);

        var measurementDto = new MeasurementDto(32f, false, sensorDto);


       postMeasurement(measurementDto)
                .andExpect(status().isOk());

        assertThat(measurementService.findAll().size()).isEqualTo(1);
    }

    @Test
    void addingIncorrectMeasurementThrowsException() throws Exception {
        var sensorDto = new SensorDto("Vic-A");

        var measurementDto = new MeasurementDto(101f, null, sensorDto);

        postMeasurement(measurementDto)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Sensor with that name does not exist")));
    }

    @Test
    void canGetAllMeasurements() throws Exception {
        var sensorDto = new SensorDto("Vic-A");
        postSensor(sensorDto);

        postMeasurement(new MeasurementDto(32f, true, sensorDto));
        postMeasurement(new MeasurementDto(15f, true, sensorDto));
        postMeasurement(new MeasurementDto(22f, false, sensorDto));

        mvc.perform(get("/measurements")).andDo(print()).andExpect(status().isOk());

        assertThat(measurementService.findAll().size()).isEqualTo(3);
    }

    @Test
    void canGetRainyDaysCount() throws Exception{
        var sensorDto = new SensorDto("Vic-A");
        postSensor(sensorDto);

        postMeasurement(new MeasurementDto(32f, true, sensorDto));
        postMeasurement(new MeasurementDto(15f, true, sensorDto));
        postMeasurement(new MeasurementDto(22f, false, sensorDto));

        mvc.perform(get("/measurements/rainy-days-count")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("2"));

        assertThat(measurementService.getRainyDaysCount()).isEqualTo(2);
    }

    private ResultActions postMeasurement(MeasurementDto measurementDto) throws Exception {
        return mvc.perform(post("/measurements/add").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(measurementDto)));
    }

    private ResultActions postSensor(SensorDto sensorDto) throws Exception {
        return mvc.perform(post("/sensors/registration")
                        .content(mapper.writeValueAsString(sensorDto))
                        .contentType(MediaType.APPLICATION_JSON));
    }

}
