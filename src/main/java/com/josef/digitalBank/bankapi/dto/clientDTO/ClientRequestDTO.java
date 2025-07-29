package com.josef.digitalBank.bankapi.dto.clientDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.josef.digitalBank.bankapi.model.ClientRole;
import com.josef.digitalBank.bankapi.validation.annotations.ValidCPF;
import com.josef.digitalBank.bankapi.validation.annotations.ValidEmail;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ClientRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String lastName;

    @ValidCPF
    private String cpf;

    @ValidEmail
    private String email;
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    private ClientRole role;

    public ClientRequestDTO(){}

    public ClientRequestDTO(String name, String lastName, String cpf, String email, String password, LocalDate birthdate, ClientRole role) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName() {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public ClientRole getRole() {
        return role;
    }

    public void setRole(ClientRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRequestDTO that = (ClientRequestDTO) o;
        return Objects.equals(name,
                that.name) && Objects.equals(lastName,
                that.lastName) && Objects.equals(cpf,
                that.cpf) && Objects.equals(email,
                that.email) && Objects.equals(password,
                that.password) && Objects.equals(birthdate,
                that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,
                lastName,
                cpf,
                email,
                password,
                birthdate);
    }
}
