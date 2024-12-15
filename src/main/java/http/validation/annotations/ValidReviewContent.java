package http.validation.annotations;

import http.validation.validators.ReviewContentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReviewContentValidator.class)
public @interface ValidReviewContent {
    String message() default "Invalid content parameter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String parameterName() default "";
}