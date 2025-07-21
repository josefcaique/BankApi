package com.josef.digitalBank.bankapi.validation.validators;
import com.josef.digitalBank.bankapi.validation.annotations.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.Length;


public class CpfValidator implements ConstraintValidator<ValidCPF, String> {
    @Override
    public void initialize(ValidCPF constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.trim().length() != 11 ) return false;
        String subCPF = cpf.substring(0, 9);
        char firstDigit = cpf.charAt(9);
        char secondDigit = cpf.charAt(10);
        int counter = 0;
        int value = 0;
        System.out.println(subCPF);
        for (int i = 0; i < subCPF.length(); i++) {
            int n = (int) subCPF.charAt(i);
            System.out.println(n);
            value = n  * (10 - i);
            System.out.println(value);
        }
        return true;
    }
}
