package com.example.authentication.application;


import com.example.DTOs.authdto.ExtractedCustomerDTO;
import com.example.domains.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public ExtractedCustomerDTO extractCustomer(String token){
        Long id = extractClaim(token, claims -> claims.get("customer_id", Long.class));
        String username = extractClaim(token, Claims::getSubject);
        String sector = extractClaim(token, claims -> claims.get("sector", String.class));
        Long version = extractClaim(token, claims -> claims.get("version", Long.class));
        return new ExtractedCustomerDTO(id, username, sector, version);
    }


    public String generateToken(User userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("customer_id", userDetails.getCustomer().getId());
        extraClaims.put("sector", userDetails.getCustomer().getSector());
        extraClaims.put("role", userDetails.getCustomer().getUser().getRole());
        extraClaims.put("version", userDetails.getCustomer().getVersion());

        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public Boolean isTokenValid(String token, User userDetails) {
        ExtractedCustomerDTO customer = extractCustomer(token);
        return customer.name().equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && userDetails.getCustomer().getVersion().equals(customer.version());
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}