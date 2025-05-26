package org.example.diplomski.data.dto.UserRelationship;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.enums.RelationshipStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationshipDto {

    private Long id;
    private Long userId;
    private String email;
    private Long followedUserId;
    private String followedEmail;
    private RelationshipStatus status;


}
