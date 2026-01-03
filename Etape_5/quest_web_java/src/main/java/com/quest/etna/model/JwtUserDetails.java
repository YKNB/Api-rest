package com.quest.etna.model;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class JwtUserDetails implements UserDetails {

    private User user;

/*
    public JwtUserDetails(User user) {
        super();
        this.user = user;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Créez une liste d'autorisations basée sur le rôle de l'utilisateur
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole())); // ROLE_USER, ROLE_ADMIN, etc.

        // Vous pouvez ajouter d'autres autorisations spécifiques ici en fonction du rôle
        if (user.getRole() == UserRole.ROLE_USER) {
            authorities.add(new SimpleGrantedAuthority("PERMISSION_READ"));
            authorities.add(new SimpleGrantedAuthority("PERMISSION_WRITE"));
        } else if (user.getRole() == UserRole.ROLE_ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ADMIN_PERMISSION"));
        }

        return authorities;
    }

    public User getUser() {
        return user;
    }
*/

    private final org.springframework.security.core.userdetails.User securityUser;

    public JwtUserDetails(User user) {
        super();
        this.user = user;
        this.securityUser = new org.springframework.security.core.userdetails.User(
                getUsername(),
                getPassword(),
                new ArrayList<>()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.securityUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}