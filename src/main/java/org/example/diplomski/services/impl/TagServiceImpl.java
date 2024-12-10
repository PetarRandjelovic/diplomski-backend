package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.TagDto;
import org.example.diplomski.data.entites.Tag;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.mapper.RoleMapper;
import org.example.diplomski.mapper.TagMapper;
import org.example.diplomski.repositories.RoleRepository;
import org.example.diplomski.repositories.TagRepository;
import org.example.diplomski.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDto getTagById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag with id: " + id + " not found."));

        return tagMapper.tagToTagDto(tag);
    }

    @Override
    public TagDto getTagByName(String name) {
        Tag tag = tagRepository.findByName(name).orElseThrow(() -> new NotFoundException("Tag with name: " + name + " not found."));

        return tagMapper.tagToTagDto(tag);
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = tagMapper.tagDtoToTag(tagDto);
        return tagMapper.tagToTagDto(tagRepository.save(tag));
    }

    @Override
    public TagDto updateTag(TagDto tagDto) {
        return null;
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag with id: " + id + " not found."));

        tagRepository.delete(tag);
    }
}
