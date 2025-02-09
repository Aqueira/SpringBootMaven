package com.example.DTOs.customerdto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record CurrentDTO(
        Collection<? extends GrantedAuthority> authorities,
                         Boolean active,
                         Boolean nonLocked,
                         Boolean isEnabled,
                         String username
)
{}
