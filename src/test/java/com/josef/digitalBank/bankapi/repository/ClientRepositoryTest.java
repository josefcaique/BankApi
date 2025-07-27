package com.josef.digitalBank.bankapi.repository;

import com.josef.digitalBank.bankapi.dto.clientDTO.ClientRequestDTO;
import com.josef.digitalBank.bankapi.model.Client;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    ClientRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get client successfully from DB")
    void findClientByCpfSuccess() {
        String cpf = "46408483828";
        ClientRequestDTO data = new ClientRequestDTO("Roberto", "Rocha", "46408483828", "jcaique10@gmail.com", "123456", LocalDate.now());
        this.createClient(data);

        Client result = this.repository.findClientByCpf(cpf);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should not get a client from db")
    void findClientByCpfFail() {
        String cpf = "46408483822";
        ClientRequestDTO data = new ClientRequestDTO("Roberto", "Rocha", "31946973076", "jcaique1@gmail.com", "123456", LocalDate.now());
        this.createClient(data);

        Client result = this.repository.findClientByCpf(cpf);
        assertNull(result);
    }

    @Test
    void findClientByEmail() {
    }

    private Client createClient(ClientRequestDTO data) {
        Client client = new Client();
        client.setName(data.getName());
        client.setLastName(data.getLastName());
        client.setBirthdate(data.getBirthdate());
        client.setCpf(data.getCpf());
        client.setEmail(data.getEmail());
        client.setPassword(data.getPassword());

        this.entityManager.persist(client);
        return client;
    }
}