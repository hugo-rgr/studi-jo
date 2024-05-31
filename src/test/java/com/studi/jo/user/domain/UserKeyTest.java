package com.studi.jo.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class UserKeyTest {

    @Test
    public void testValidUserKeyUniqueness() {
        UserKey userKey1 = new UserKey();
        UserKey userKey2 = new UserKey();
        assertNotEquals(userKey1.getValue(), userKey2.getValue(), "User keys should be unique");
    }
}

