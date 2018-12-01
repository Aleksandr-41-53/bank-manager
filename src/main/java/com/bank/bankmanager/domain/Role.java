package com.bank.bankmanager.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
