package main.blps_lab4.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleEnum implements GrantedAuthority {
    ADMIN("ADMIN"),
    CLIENT("CLIENT");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
