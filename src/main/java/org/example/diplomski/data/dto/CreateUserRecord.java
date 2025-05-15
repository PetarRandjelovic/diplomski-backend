package org.example.diplomski.data.dto;

import lombok.Getter;
import org.example.diplomski.data.enums.RoleType;

public record CreateUserRecord(Long id,
                               Long dateOfBirth,
                               String email,
                               String username,
                               RoleType role,
                               String password) {
}
