package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.TagDto;
import org.example.diplomski.data.entites.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagDto tagToTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }
    public Tag tagDtoToTag(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }
}
