package http.validation.validators;

import http.validation.annotations.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    private String parameterName;

    @Override
    public void initialize(ValidYear constraintAnnotation) {
        parameterName = constraintAnnotation.parameterName();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value == null) {
            context.buildConstraintViolationWithTemplate("Missing " + parameterName + " parameter")
                    .addConstraintViolation();

            return false;
        }

        if (value < 0 || value > getCurrentYear()) {
            context.buildConstraintViolationWithTemplate("Invalid " + parameterName + " parameter")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    private int getCurrentYear() {
        return Year.now().getValue();
    }
}
