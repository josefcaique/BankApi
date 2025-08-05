package com.josef.digitalBank.bankapi.controller;
import com.josef.digitalBank.bankapi.dto.clientDTO.ClientRequestDTO;
import com.josef.digitalBank.bankapi.dto.clientDTO.ClientResponseDTO;
import com.josef.digitalBank.bankapi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("api/client")
public class ClientController {

    @Autowired
    ClientService service;

    @GetMapping(value="/{id}")
    public ClientResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<ClientResponseDTO> findAll(){
        return service.findAll();
    }

    @PostMapping()
    public ClientResponseDTO create(@Valid @RequestBody ClientRequestDTO client) {
        return service.create(client);
    }

    @PutMapping(value = "/{id}")
    public ClientResponseDTO update(@PathVariable("id") Long id, @RequestBody ClientRequestDTO client) {
        return service.update(id, client);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
