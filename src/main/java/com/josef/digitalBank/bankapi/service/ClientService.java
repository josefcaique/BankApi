package com.josef.digitalBank.bankapi.service;

import com.josef.digitalBank.bankapi.dto.clientDTO.ClientResponseDTO;
import com.josef.digitalBank.bankapi.exceptions.ResourceNotFoundException;
import com.josef.digitalBank.bankapi.mapper.ObjectMapper;
import com.josef.digitalBank.bankapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository repo;

    public ClientResponseDTO findById(Long id) {
        var entity =  repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));

        return ObjectMapper.parseObject(entity, ClientResponseDTO.class);
    }
}
