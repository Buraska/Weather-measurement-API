package application.weatherMeasurement.utils.exceptions;

public class SensorCannotBeCreated extends RuntimeException{

    public SensorCannotBeCreated(String msg){
        super(msg);
    }
}
