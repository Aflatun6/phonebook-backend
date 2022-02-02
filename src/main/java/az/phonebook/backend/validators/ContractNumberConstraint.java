package az.phonebook.backend.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = ContractNumberValidator.class)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContractNumberConstraint {

    /**
     * Error message to display.
     *
     * @return
     */
    String message() default "Invalid contract number";

    /**
     * Error grouping.
     *
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * Metadata information.
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};
}
