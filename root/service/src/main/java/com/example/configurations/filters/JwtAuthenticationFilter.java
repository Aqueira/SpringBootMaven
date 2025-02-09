package com.example.configurations.filters;


import com.example.DTOs.authdto.ExtractedCustomerDTO;
import com.example.authentication.application.JWTService;
import com.example.configurations.filters.mapper.FilterMapper;
import com.example.contracts.UserService;
import com.example.domains.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;
    private final FilterMapper filterMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            ExtractedCustomerDTO extractedCustomerDTO = jwtService.extractCustomer(token);
            if (!extractedCustomerDTO.name().isEmpty()) {
                User user = filterMapper.responseDTOToEntity(userService.read(extractedCustomerDTO.id()));
                if (jwtService.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
