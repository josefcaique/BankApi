package com.josef.digitalBank.bankapi.repository;

import com.josef.digitalBank.bankapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
}
