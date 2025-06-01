package org.example.diplomski.data.dto.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikePostDto {

    private Long id;
    private Long postId;
    private String email;

}
