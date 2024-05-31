package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        FirstName firstName = new FirstName("Agent");
        LastName lastName = new LastName("Smith");
        Email email = new Email("agent.smith@example.com");
        Password password = new Password("APassword6!");
        UserKey userKey = new UserKey();
        Role role = Role.CLIENT;

        user = new User(null, firstName, lastName, email, password, userKey, role);
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_CLIENT", authorities.iterator().next().getAuthority());
    }

    @Test
    public void testGetPassword() {
        String password = user.getPassword();
        assertNotNull(password);
        assertNotEquals("APassword6!", password); //password should be encoded
    }

    @Test
    public void testGetUsername() {
        String username = user.getUsername();
        assertNotNull(username);
        assertEquals("agent.smith@example.com", username);
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(user.isEnabled());
    }
}
