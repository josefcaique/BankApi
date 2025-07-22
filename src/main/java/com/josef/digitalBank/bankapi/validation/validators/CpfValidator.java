package com.josef.digitalBank.bankapi.validation.validators;
import com.josef.digitalBank.bankapi.validation.annotations.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.Length;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class CpfValidator implements ConstraintValidator<ValidCPF, String> {
    @Override
    public void initialize(ValidCPF constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.trim().length() != 11 ) return false;
        String subCpf = cpf.substring(0, 9);
        List<Character> subCpfList = new ArrayList<>(subCpf.chars().mapToObj(c -> (char) c).toList());
        int digit = getNewDigit(subCpfList);
        subCpfList.add((char) (digit + '0'));
        int digit2 = getNewDigit(subCpfList);
        subCpfList.add((char) (digit2 + '0'));

        String newString = subCpfList.stream().map(String::valueOf).collect(Collectors.joining());
        System.out.println(newString);
        System.out.println(cpf);
        return newString.equals(cpf);
    }

    public int getNewDigit(List<Character> subCpfList) {
        int value = 0;

        for (int i=0; i<subCpfList.size(); i++) {
            char n = subCpfList.get(i);
            value += Character.getNumericValue(n) * (subCpfList.size()+1-i);
        }
        System.out.println(value);
        int rest = value % 11;
        System.out.println(rest);
        int generatedDigit = 0;
        if (rest > 2) {
            generatedDigit = 11 - rest;
        }
        System.out.println(generatedDigit);
        return generatedDigit;
    }
}
