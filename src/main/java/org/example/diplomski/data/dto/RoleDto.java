package org.example.diplomski.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.enums.RoleType;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private RoleType roleType;
}
