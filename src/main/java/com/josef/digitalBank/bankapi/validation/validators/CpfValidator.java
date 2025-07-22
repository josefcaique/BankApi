package com.josef.digitalBank.bankapi.validation.validators;
import com.josef.digitalBank.bankapi.validation.annotations.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.Length;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CpfValidator implements ConstraintValidator<ValidCPF, String> {
    @Override
    public void initialize(ValidCPF constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.trim().length() != 11 ) return false;
        List<Character> subCpfList = new ArrayList<>();

        int value = 0;

        for (int i=0; i<subCpfList.size(); i++) {
            char n = subCpfList.get(i);
            value += Character.getNumericValue(n) * (10-i);
        }
        int rest = value % 11;
        System.out.println(rest);
        if (rest < 2) {
            int generatedValue = 0;
        } else {
            int generatedValue = 11 - rest;
        }





        return true;
    }
}
