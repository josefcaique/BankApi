package com.josef.digitalBank.bankapi.service;

import com.josef.digitalBank.bankapi.dto.ClientDTO;
import com.josef.digitalBank.bankapi.exceptions.ResourceNotFoundException;
import com.josef.digitalBank.bankapi.mapper.ObjectMapper;
import com.josef.digitalBank.bankapi.model.Client;
import com.josef.digitalBank.bankapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repo;

    public ClientDTO findById(Long id) {
        var entity =  repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));

        return ObjectMapper.parseObject(entity, ClientDTO.class);
    }
}
