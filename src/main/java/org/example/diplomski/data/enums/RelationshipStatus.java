package org.example.diplomski.data.enums;

import lombok.Getter;

@Getter
public enum RelationshipStatus {

    CONFIRMED("CONFIRMED"),
    DECLINED("DECLINED"),
    WAITING("WAITING");


    private final String relationshipStatus;

    RelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }
}
