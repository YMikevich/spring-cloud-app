package com.github.ymikevich.es.integration.api.requests.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnumValue, Object> {

    private Object[] enumValues;

    @Override
    public void initialize(final ValidEnumValue constraintAnnotation) {
        enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String contextValue = value.toString();
        return Stream.of(enumValues)
                .map(Object::toString)
                .anyMatch(enumValues -> enumValues.equals(contextValue));
    }
}
