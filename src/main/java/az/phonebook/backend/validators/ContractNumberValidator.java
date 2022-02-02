package az.phonebook.backend.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class ContractNumberValidator implements ConstraintValidator<ContractNumberConstraint, String> {

    private static final String NUMBER_PATTERN = "\\d+";
    private static final String CARD_NUMBER_PATTERN = "\\d{15,20}";
    private static final String CONTRACT_NUMBER_PATTERN = "^[\\w_()-]{1,100}";

    @Override
    public boolean isValid(@NotNull String value, ConstraintValidatorContext context) {
        if (value.matches(NUMBER_PATTERN)) {
            return value.matches(CARD_NUMBER_PATTERN);
        } else {
            return value.matches(CONTRACT_NUMBER_PATTERN);
        }
    }
}
