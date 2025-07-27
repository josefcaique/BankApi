package com.josef.digitalBank.bankapi.repository;

import com.josef.digitalBank.bankapi.dto.clientDTO.ClientRequestDTO;
import com.josef.digitalBank.bankapi.model.Client;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
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

    @BeforeEach
    void setupAll() {
        ClientRequestDTO data = new ClientRequestDTO("Roberto", "Rocha", "31946973076", "roberto@email.com", "123456", LocalDate.now());
        createClient(data);
    }


    @Test
    @DisplayName("Should get client by cpf successfully from DB")
    void findClientByCpfSuccess() {
        String cpf = "31946973076";
        Client result = this.repository.findClientByCpf(cpf);

        assertNotNull(result, "Expected client to be found, but got null");
        assertEquals("Roberto", result.getName());
        assertEquals("Rocha", result.getLastName());
        assertEquals("31946973076", result.getCpf());
        assertEquals("roberto@email.com", result.getEmail());
        assertEquals("123456", result.getPassword());

    }

    @Test
    @DisplayName("Should not get a client by cpf from db")
    void findClientByCpfFail() {
        String cpf = "31946973099";
        Client result = this.repository.findClientByCpf(cpf);
        assertNull(result, "expected no client to be found, but found one");
    }

    @Test
    @DisplayName("Should get a client by email from db")
    void findClientByEmailSuccess() {
        String email = "roberto@email.com";
        Client result = this.repository.findClientByEmail(email);

        assertNotNull(result, "Expected client to be found, but got null");
        assertEquals("Roberto", result.getName());
        assertEquals("Rocha", result.getLastName());
        assertEquals("31946973076", result.getCpf());
        assertEquals("roberto@email.com", result.getEmail());
        assertEquals("123456", result.getPassword());
    }

    @Test
    @DisplayName("Should not get a client by email from db")
    void findClientByEmailFail() {
        String email = "emailNotExist@gmail.com";
        Client result = this.repository.findClientByEmail(email);
        assertNull(result, "expected no client to be found, but found one");
    }




    private void createClient(ClientRequestDTO data) {
        Client client = new Client();
        client.setName(data.getName());
        client.setLastName(data.getLastName());
        client.setBirthdate(data.getBirthdate());
        client.setCpf(data.getCpf());
        client.setEmail(data.getEmail());
        client.setPassword(data.getPassword());

        this.entityManager.persist(client);
        //return client;
    }
}