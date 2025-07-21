package com.josef.digitalBank.bankapi.validation.annotations;

import com.josef.digitalBank.bankapi.validation.validators.CpfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD }) // Pode ser usada em atributos
@Retention(RetentionPolicy.RUNTIME) // Disponível em tempo de execução
@Constraint(validatedBy = CpfValidator.class) // Define quem vai validar
public @interface ValidCPF {
    String message() default "CPF inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
