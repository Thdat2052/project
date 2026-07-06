package com.example.common.service.securities;

import com.example.common.service.entities.RoleEntity;
import com.example.common.service.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetail implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(Long id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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

    public static CustomUserDetail build(UserEntity user, List<RoleEntity> roles) {
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new CustomUserDetail(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                authorities
        );
    }
}
