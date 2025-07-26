package com.josef.digitalBank.bankapi.service;

import com.josef.digitalBank.bankapi.dto.clientDTO.ClientRequestDTO;
import com.josef.digitalBank.bankapi.dto.clientDTO.ClientResponseDTO;
import com.josef.digitalBank.bankapi.exceptions.ResourceNotFoundException;
import com.josef.digitalBank.bankapi.mapper.ObjectMapper;
import com.josef.digitalBank.bankapi.model.Client;
import com.josef.digitalBank.bankapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository repo;

    public ClientResponseDTO findById(Long id) {
        var entity =  repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));

        return ObjectMapper.parseObject(entity, ClientResponseDTO.class);
    }

    public List<ClientResponseDTO> findAll() {
        List<Client> entities = repo.findAll();
        return ObjectMapper.parseListObjects(entities, ClientResponseDTO.class);
    }

    public ClientResponseDTO create(ClientRequestDTO client) {
        client.setCpf(client.getCpf().replaceAll("[^\\d]", ""));
        var entity = repo.save(ObjectMapper.parseObject(client, Client.class));
        return ObjectMapper.parseObject(entity, ClientResponseDTO.class);
    }

    public ClientResponseDTO update(Long id, ClientRequestDTO client) {
        ClientResponseDTO entity = findById(id);

        System.out.println(entity.getId());
        entity.setName(client.getName());
        entity.setLastName(client.getLastName());
        entity.setEmail(client.getEmail());
        entity.setCpf(client.getCpf());
        entity.setBirthdate(client.getBirthdate());
        var updatedClient = repo.save(ObjectMapper.parseObject(entity, Client.class));
        return ObjectMapper.parseObject(updatedClient, ClientResponseDTO.class);
    }

    public void delete(Long id) {
        var entity = findById(id);
        repo.delete(ObjectMapper.parseObject(entity, Client.class));
    }

}
