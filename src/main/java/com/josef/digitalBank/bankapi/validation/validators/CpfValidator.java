package com.josef.digitalBank.bankapi.validation.validators;
import com.josef.digitalBank.bankapi.validation.annotations.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
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
        return newString.equals(cpf);
    }

    public int getNewDigit(List<Character> subCpfList) {
        int value = 0;

        for (int i=0; i<subCpfList.size(); i++) {
            char n = subCpfList.get(i);
            value += Character.getNumericValue(n) * (subCpfList.size()+1-i);
        }
        int rest = value % 11;
        int generatedDigit = 0;
        if (rest > 2) {
            generatedDigit = 11 - rest;
        }
        return generatedDigit;
    }

    public boolean numberEquals(String cpf) {
        String num = null;
        for (char n : cpf.toCharArray()) {
            if (num == null) {
                num = n+"";
            }
            if (n+"" != num) return false;
        }
        return true;
    }
}
