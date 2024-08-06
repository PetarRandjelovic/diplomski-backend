package org.example.diplomski.data.enums;

import lombok.Getter;

@Getter
public enum MediaType {

    IMAGE("IMAGE"),
    VIDEO("VIDEO");


    private final String mediaType;

    MediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    }
