package org.example.diplomski.data.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.enums.RoleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private Long dateOfBirth;
    private String email;
    private String username;
    private RoleType role;

}
