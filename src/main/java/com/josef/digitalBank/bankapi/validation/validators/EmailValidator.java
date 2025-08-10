package com.josef.digitalBank.bankapi.validation.validators;
import com.josef.digitalBank.bankapi.validation.annotations.ValidCPF;
import com.josef.digitalBank.bankapi.validation.annotations.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return (email.contains("@") && countAtSign(email) == 1 && (email.endsWith(".com") || email.endsWith(".com.br")));
    }

    public int countAtSign(String string) {
        int counter = 0;
        for (int i=0; i<string.length(); i++) {
            if ("@".equals(string.charAt(i)+"")) {
                counter++;
            }
        }
        return counter;
    }
}
