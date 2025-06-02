package org.example.diplomski.data.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.dto.MediaDto;
import org.example.diplomski.data.dto.TagDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String userEmail;

    private String content;

    private List<TagDto> tags;

    private Long creationDate;

    private List<MediaDto> media;

}
