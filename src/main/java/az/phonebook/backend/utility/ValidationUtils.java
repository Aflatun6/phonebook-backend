package az.phonebook.backend.utility;

import az.phonebook.backend.exceptions.ValidationException;

public abstract class ValidationUtils {
    public static void validateContractNumber(String value) {
        if (!value.matches("[a-zA-Z0-9]{15,100}")) {
            throw new ValidationException(ErrorConstants.CONTRACT_NUMBER_NOT_MATCH);
        }
    }
}
