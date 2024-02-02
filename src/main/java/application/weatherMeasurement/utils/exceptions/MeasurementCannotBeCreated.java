package application.weatherMeasurement.utils.exceptions;

public class MeasurementCannotBeCreated extends RuntimeException{

    public MeasurementCannotBeCreated(String msg){
        super(msg);
    }
    public MeasurementCannotBeCreated(){}
}
