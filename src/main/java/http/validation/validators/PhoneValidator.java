package http.validation.validators;

import http.validation.annotations.ValidPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private String regexp;

    @Override
    public void initialize(final ValidPhone constraintAnnotation) {
        regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isBlank() && value.matches(regexp);
    }
}
