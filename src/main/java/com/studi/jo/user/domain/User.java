package com.studi.jo.user.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

// Utilisateurs

@Entity
@Table(name = "user_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    FirstName firstName;

    @Embedded
    LastName lastName;

    @Embedded
    Email email;

    @Embedded
    Password pass;

    @Embedded
    UserKey userKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.getRole().getAuthority()));
    }

    @Override
    public String getPassword() {
        return this.getPass().getValue();
    }

    @Override
    public String getUsername() {
        return this.getEmail().getValue();
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
