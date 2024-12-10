package org.example.diplomski.services;

import org.example.diplomski.data.dto.TagDto;
import org.springframework.stereotype.Service;

@Service
public interface TagService {

    TagDto getTagById(Long id);
    TagDto getTagByName(String name);
    TagDto createTag(TagDto tagDto);
    TagDto updateTag(TagDto tagDto);
    void deleteTag(Long id);
}
