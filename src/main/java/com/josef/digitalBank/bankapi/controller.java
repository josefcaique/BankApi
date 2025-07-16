package com.josef.digitalBank.bankapi;

import com.josef.digitalBank.bankapi.dto.clientDTO.ClientResponseDTO;
import com.josef.digitalBank.bankapi.model.Client;
import com.josef.digitalBank.bankapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/client")
public class controller {

    @Autowired
    ClientService service;

    @GetMapping(value="/{id}")
    public ClientResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public ClientResponseDTO create(@RequestBody Client client) {
        return service.create(client);
    }
}
