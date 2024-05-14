package main.blps_lab2.security;

import main.blps_lab2.data.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static AuthUserDetails build(Client client) {
        List<GrantedAuthority> authorities = new ArrayList<>() {{
            add(new SimpleGrantedAuthority(Role.CLIENT.getName()));
        }};

        return new AuthUserDetails(client.getEmail(), client.getPassword(), authorities);
    }

    public static AuthUserDetails build(Admin admin) {
        List<GrantedAuthority> authorities = new ArrayList<>() {{
            add(new SimpleGrantedAuthority(Role.ADMIN.getName()));
        }};

        return new AuthUserDetails(admin.getName(), admin.getPassword(), authorities);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
