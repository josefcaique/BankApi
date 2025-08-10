package com.josef.digitalBank.bankapi.controller;

import com.josef.digitalBank.bankapi.dto.authDTO.AuthDTO;
import com.josef.digitalBank.bankapi.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@RequestBody AuthDTO authDTO) {
        if (credentialsNull(authDTO)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = service.signIn(authDTO);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    @PutMapping(value="/refresh/{username}")
    public ResponseEntity<?> refresh(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        if (parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = service.refreshToken(username, refreshToken);
        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsNull(AuthDTO authDTO) {
        return authDTO == null || StringUtils.isBlank(authDTO.password()) || StringUtils.isBlank(authDTO.login());
    }
}
