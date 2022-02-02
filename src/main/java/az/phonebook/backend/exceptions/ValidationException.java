package az.phonebook.backend.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String ex) {
        super(ex);
    }
}
