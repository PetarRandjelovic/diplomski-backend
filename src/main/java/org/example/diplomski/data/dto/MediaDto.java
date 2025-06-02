package org.example.diplomski.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.enums.MediaType;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {

    private Long id;
    private Long postId;
    private String url;
    private String title;
    private MediaType type;

}
