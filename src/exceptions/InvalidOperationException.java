package exceptions;

public class InvalidOperationException extends Exception{
    public InvalidOperationException(String outputToPrint) {
        super(outputToPrint);
    }
}
