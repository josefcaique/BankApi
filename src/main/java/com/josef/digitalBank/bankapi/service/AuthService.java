package com.josef.digitalBank.bankapi.service;

import com.josef.digitalBank.bankapi.dto.authDTO.AuthDTO;
import com.josef.digitalBank.bankapi.dto.authDTO.TokenDTO;
import com.josef.digitalBank.bankapi.repository.ClientRepository;
import com.josef.digitalBank.bankapi.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    ClientRepository repository;

    final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ResponseEntity<TokenDTO> signIn(AuthDTO dto) {

        logger.info("Logging a client!");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.login(),
                        dto.password()
                )
        );

        var client = repository.findClientByEmail(dto.login());
        System.out.println(client);
        if (client == null) throw new UsernameNotFoundException("Username not found!");

        var token = tokenProvider.createAccessToken(dto.login(), dto.role());
        return ResponseEntity.ok(token);
    }

    public String generateHashedPassword(String password){
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,
        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);

    }
}
