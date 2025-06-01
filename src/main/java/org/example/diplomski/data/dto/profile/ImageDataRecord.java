package org.example.diplomski.data.dto.profile;

public record ImageDataRecord(
         Long id,
         String name,
         String type,
         byte[] imageData
) {
}
