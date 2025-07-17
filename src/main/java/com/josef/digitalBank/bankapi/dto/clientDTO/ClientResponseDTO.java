package com.josef.digitalBank.bankapi.dto.clientDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ClientResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String email;
    private Date birthdate;

    public ClientResponseDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setLastName(String lastName) {
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientResponseDTO that = (ClientResponseDTO) o;
        return Objects.equals(id,
                that.id) && Objects.equals(name,
                that.name) && Objects.equals(lastName,
                that.lastName) && Objects.equals(cpf,
                that.cpf) && Objects.equals(email,
                that.email) && Objects.equals(birthdate,
                that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                name,
                lastName,
                cpf,
                email,
                birthdate);
    }
}
