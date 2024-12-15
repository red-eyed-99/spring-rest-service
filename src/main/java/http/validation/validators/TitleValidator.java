package http.validation.validators;

import http.validation.annotations.ValidTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<ValidTitle, String> {

    private static final int MAX_LENGTH = 255;

    private String parameterName;

    @Override
    public void initialize(ValidTitle constraintAnnotation) {
        parameterName = constraintAnnotation.parameterName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value == null || value.isBlank()) {
            context.buildConstraintViolationWithTemplate("Missing " + parameterName + " parameter")
                    .addConstraintViolation();

            return false;
        }

        if (value.length() > MAX_LENGTH) {
            context.buildConstraintViolationWithTemplate(parameterName + " must be no more than " + MAX_LENGTH + " characters")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
