package org.example.diplomski.data.dto.UserRelationship;

import org.example.diplomski.data.enums.RelationshipStatus;

public record UserRelationshipRecord(String user1, String user2, RelationshipStatus status) {
}
