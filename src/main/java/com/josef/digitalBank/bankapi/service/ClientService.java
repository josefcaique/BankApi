package com.josef.digitalBank.bankapi.service;

import com.josef.digitalBank.bankapi.dto.clientDTO.ClientRequestDTO;
import com.josef.digitalBank.bankapi.dto.clientDTO.ClientResponseDTO;
import com.josef.digitalBank.bankapi.exceptions.UserAlreadyExists;
import com.josef.digitalBank.bankapi.exceptions.ResourceNotFoundException;
import com.josef.digitalBank.bankapi.mapper.ObjectMapper;
import com.josef.digitalBank.bankapi.model.Client;
import com.josef.digitalBank.bankapi.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements UserDetailsService {

    final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    ClientRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientResponseDTO findById(Long id) {
        logger.info("Finding a client by Id");

        var entity =  repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));

        return ObjectMapper.parseObject(entity, ClientResponseDTO.class);
    }

    public List<ClientResponseDTO> findAll() {
        logger.info("Finding all clients");

        List<Client> entities = repo.findAll();
        return ObjectMapper.parseListObjects(entities, ClientResponseDTO.class);
    }

    public ClientResponseDTO create(ClientRequestDTO client) {

        logger.info("Crating a new client");

        client.setCpf(client.getCpf().replaceAll("[^\\d]", ""));
        if (repo.findClientByCpf(client.getCpf()) != null) throw new UserAlreadyExists("There is already a client with that cpf");
        if (repo.findClientByEmail(client.getEmail()) != null) throw new UserAlreadyExists("There is already a client with that email");

        client.setPassword(passwordEncoder.encode(client.getPassword()));

        var entity = repo.save(ObjectMapper.parseObject(client, Client.class));
        return ObjectMapper.parseObject(entity, ClientResponseDTO.class);
    }

    public ClientResponseDTO update(Long id, ClientRequestDTO client) {

        logger.info("Updating a client");

        ClientResponseDTO entity = findById(id);
        entity.setName(client.getName());
        entity.setLastName(client.getLastName());
        entity.setEmail(client.getEmail());
        entity.setCpf(client.getCpf());
        entity.setBirthdate(client.getBirthdate());
        var updatedClient = repo.save(ObjectMapper.parseObject(entity, Client.class));
        return ObjectMapper.parseObject(updatedClient, ClientResponseDTO.class);
    }

    public void delete(Long id) {

        logger.info("Deleting a client");

        var entity = findById(id);
        repo.delete(ObjectMapper.parseObject(entity, Client.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var client = repo.findClientByEmail(username);
        if (client != null) return client;
        else throw new UsernameNotFoundException("There is no client with that email!");
    }
}
