package exceptions;

public class InsufficientFuelException extends Exception{
    public InsufficientFuelException(String outputToPrint){
        super(outputToPrint);
    }
}
