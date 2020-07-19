package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.exception.EmptyCommentException;
import com.travelplanner.travelplanner_server.model.Comment;
import com.travelplanner.travelplanner_server.mongodb.dal.CommentDAL;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.restservice.payload.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private final UserDAL userDAL;
    private final CommentDAL commentDAL;

    public CommentController(UserDAL userDAL, CommentDAL commentDAL) {
        this.userDAL = userDAL;
        this.commentDAL = commentDAL;
    }

    @PostMapping(value = "/comment")
    public void comment(@RequestBody CommentRequest commentRequest) {
        if (commentRequest.getContent() == null || commentRequest.getContent().length() == 0) {
            throw new EmptyCommentException();
        }
        Comment comment = Comment.builder()
                .username(commentRequest.getUsername())
                .place_id(commentRequest.getPlace_id())
                .content(commentRequest.getContent())
                .createTime("dateCreated")
                .modifyTime("dateModified")
                .build();
        commentDAL.createComment(comment);
    }
}