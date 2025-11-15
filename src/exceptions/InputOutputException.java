package exceptions;

public class InputOutputException extends Exception {
    public InputOutputException(String outputToPrint) {
        super(outputToPrint);
    }
}
