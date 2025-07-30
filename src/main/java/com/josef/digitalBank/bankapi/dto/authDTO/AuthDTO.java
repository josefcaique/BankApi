package com.josef.digitalBank.bankapi.dto.authDTO;

import com.josef.digitalBank.bankapi.model.ClientRole;

public record AuthDTO(String login, String password, ClientRole role) {
}
