package com.automobilepartnership.security;

import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final int age;
    private final Role role;
    private String provider;
    private Map<String, Object> attributes;

    // UserDetailsService
    public UserPrincipal(Member member) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        age = member.getAge();
        role = member.getRole();
    }

    // OAuth2UserServiceImpl
    public UserPrincipal(Member member, String provider, Map<String, Object> attributes) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        name = member.getName();
        age = member.getAge();
        role = member.getRole();
        this.provider = provider;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getKey()));

        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
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

    @Override
    public String getName() {
        return null;
    }
}