package org.example.diplomski.data.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");


    private final String role;

    RoleType(String role) {
        this.role = role;
    }

}
