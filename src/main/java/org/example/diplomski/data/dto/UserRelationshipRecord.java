package org.example.diplomski.data.dto;

import org.example.diplomski.data.enums.RelationshipStatus;

public record UserRelationshipRecord(String user1, String user2, RelationshipStatus status) {
}
