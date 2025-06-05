package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.CommentDto;
import org.example.diplomski.data.entites.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setUsername(comment.getUser().getUsername());
        commentDto.setContent(comment.getContent());
        return commentDto;
    }

//    Comment commentDtoToComment(CommentDto commentDto) {
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setContent(commentDto.getContent());
//        return comment;
//    }

}
