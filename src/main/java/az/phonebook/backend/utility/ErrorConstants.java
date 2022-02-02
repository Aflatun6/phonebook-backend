package az.phonebook.backend.utility;

public final class ErrorConstants {
    public static final String TYPE_MISMATCH = "Request param type mismatch!";
    public static final String VALIDATION_EXCEPTION = "Validation exception!";

    public static final String CIF_PRESENT = "Cif is not provided!";
    public static final String CIF_LENGTH = "Cif must be 7 character!";
    public static final String CIF_TYPE = "Cif should be numeric!";
    public static final String CONTRACT_NUMBER_NOT_MATCH = "Contract number must be at least 15 characters";

    public static final String DATA_INVALID_UNIQUE_VALUE = "More than one value found";

    public static final String CONTRACT_NO_DATE_NOT_PRESENT = "Date not found with given contract";
    public static final String FILTER_TYPE_NOT_PRESENT = "Filter type is not provided!";
    public static final String FILTER_VALUE_NOT_PRESENT = "Filter value is not provided!";
    public static final String FILTER_VALUE_NOT_MATCH = "Filter value doesn't match with regex";

    public static final String INVALID_PAN = "Invalid pan number!";
    public static final String PAN_ENCODE = "Pan encode failed!";
    public static final String PAN_ENCODE_DETAIL = "Pan encoding service is not working!";

    private ErrorConstants() {
    }
}
