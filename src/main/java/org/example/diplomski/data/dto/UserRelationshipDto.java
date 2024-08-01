package org.example.diplomski.data.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationshipDto {

    private Long id;
    private Long userId;
    private String email;
    private Long followedUserId;
    private String followedEmail;


}
