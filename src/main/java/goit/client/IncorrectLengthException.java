package goit.client;

public class IncorrectLengthException extends Exception {
    public IncorrectLengthException() {
        super("Length of name should be in a valid range");
    }
}
