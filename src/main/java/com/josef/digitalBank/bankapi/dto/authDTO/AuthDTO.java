package com.josef.digitalBank.bankapi.dto.authDTO;


public record AuthDTO(String login, String password, ClientRole role) {
}
