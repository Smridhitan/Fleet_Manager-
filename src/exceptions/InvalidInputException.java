package exceptions;

public class InvalidInputException extends Exception {
    public InvalidInputException(String outputToPrint) {
        super(outputToPrint);
    }
}
