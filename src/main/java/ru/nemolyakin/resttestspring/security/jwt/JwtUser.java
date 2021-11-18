package ru.nemolyakin.resttestspring.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String username, String firstName, String lastName,
                   String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.authorities = authorities;
    }

    @JsonIgnore
    public Long getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    @JsonIgnore
    public boolean isAccountNonExpired(){
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked(){
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired(){
        return true;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    @JsonIgnore
    public String getPassword(){
        return password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @JsonIgnore
    public boolean isEnabled(){
        return true;
    }
}