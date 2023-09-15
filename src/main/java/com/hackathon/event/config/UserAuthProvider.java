package com.hackathon.event.config;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hackathon.event.dto.UserDto;
import com.hackathon.event.repository.UserRepo;
import com.hackathon.event.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import com.auth0.jwt.algorithms.Algorithm;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    private final UserService userService;
    private final UserRepo userRepo;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 sat

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return com.auth0.jwt.JWT.create()
                .withSubject(user.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("userid", user.getUserid())
                .withClaim("email", user.getEmail())
                .withClaim("firstName", user.getFirstname())
                .withClaim("lastName", user.getLastname())
                .withClaim("address", user.getAddress())
                .withClaim("housenumber", user.getHouseNumber())
                .withClaim("country", user.getCountry())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = UserDto.builder()
                .login(decoded.getSubject())
                .userid(decoded.getClaim("userid").asLong())
                .firstname(decoded.getClaim("firstName").asString())
                .lastname(decoded.getClaim("lastName").asString())
                .address(decoded.getClaim("address").asString())
                .houseNumber(decoded.getClaim("housenumber").asString())
                .country(decoded.getClaim("country").asString())
                .email(decoded.getClaim("email").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByUsername(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
