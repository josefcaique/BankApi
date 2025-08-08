package com.josef.digitalBank.bankapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.josef.digitalBank.bankapi.dto.authDTO.AuthDTO;
import com.josef.digitalBank.bankapi.dto.authDTO.TokenDTO;
import com.josef.digitalBank.bankapi.exceptions.InvalidJwtAuthenticationException;
import com.josef.digitalBank.bankapi.model.ClientRole;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.management.relation.Role;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Long validityInMilliSeconds;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(String username, ClientRole role) {
        Date now = new Date();
        Date validty = new Date(now.getTime() + validityInMilliSeconds);
        String accessToken = getAccessToken(username, role, now, validty);
        String refreshToken = getRefreshToken(username, role, now);
        return new TokenDTO(username, true, now, validty, accessToken, refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken) {
        var token = "";
        if (refreshTokenContainsBearer(refreshToken)) {
            token = refreshToken.substring("Bearer ".length());
        }

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        ClientRole role = ClientRole.valueOf(decodedJWT.getClaim("role").asString());
        return createAccessToken(username, role);
    }

    private static boolean refreshTokenContainsBearer(String refreshToken) {
        return StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ");
    }

    private String getRefreshToken(String username, ClientRole role, Date now) {
        Date refreshTokenValidity = new Date(now.getTime() + validityInMilliSeconds * 3);
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(refreshTokenValidity)
                .withSubject(username)
                .sign(algorithm);
    }

    private String getAccessToken(String username, ClientRole role, Date now, Date validty) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(validty)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm);

    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        return verifier.verify(token);

    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (refreshTokenContainsBearer(bearerToken)) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT Token!");
        }

    }

}
