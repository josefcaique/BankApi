package com.josef.digitalBank.bankapi.service;

import com.josef.digitalBank.bankapi.dto.authDTO.AuthDTO;
import com.josef.digitalBank.bankapi.dto.authDTO.TokenDTO;
import com.josef.digitalBank.bankapi.repository.ClientRepository;
import com.josef.digitalBank.bankapi.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    ClientRepository repository;

    public ResponseEntity<TokenDTO> signIn(AuthDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.login(),
                        dto.password()
                )
        );

        var client = repository.findClientByEmail(dto.login());
        if (client == null) throw new UsernameNotFoundException("Username not found!");

        var token = tokenProvider.createAccessToken(dto.login(), dto.role());

        return ResponseEntity.ok(token);
    }
}
