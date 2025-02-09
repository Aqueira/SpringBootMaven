package com.example.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


@RequiredArgsConstructor
@Getter
public enum Role {
    USER(Collections.emptySet()),

    ADMIN(EnumSet.of(
            Permission.ADMIN_CREATE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_READ,
            Permission.MANAGER_CREATE,
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_DELETE
    )
    ),

    MANAGER(EnumSet.of(
            Permission.MANAGER_CREATE,
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_DELETE
    )
    );
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;
    }
}
