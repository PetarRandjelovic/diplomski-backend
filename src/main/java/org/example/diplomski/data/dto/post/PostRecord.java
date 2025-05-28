package org.example.diplomski.data.dto.post;

import org.example.diplomski.data.dto.TagDto;

import java.util.List;

public record PostRecord(
         Long id,
         String userEmail,
         String content,
         List<TagDto>tags,
         Long creationDate,
         int likes
) {
}
