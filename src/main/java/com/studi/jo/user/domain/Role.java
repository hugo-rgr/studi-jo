package com.studi.jo.user.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, EMPLOYEE, ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
