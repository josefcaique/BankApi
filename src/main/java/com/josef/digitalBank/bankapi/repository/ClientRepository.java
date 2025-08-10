package com.josef.digitalBank.bankapi.repository;

import com.josef.digitalBank.bankapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.cpf =:cpf")
    Client findClientByCpf(@Param("cpf") String cpf);

    @Query("SELECT c FROM Client c WHERE c.email =:email")
    Client findClientByEmail(@Param("email") String email);
}
