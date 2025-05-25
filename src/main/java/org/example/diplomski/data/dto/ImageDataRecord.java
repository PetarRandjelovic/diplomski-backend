package org.example.diplomski.data.dto;

public record ImageDataRecord(
         Long id,
         String name,
         String type,
         byte[] imageData
) {
}
