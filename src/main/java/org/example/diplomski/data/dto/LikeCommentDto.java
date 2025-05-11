package org.example.diplomski.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeCommentDto {

    private Long id;
    private String email;
    private Long commentId;
}
