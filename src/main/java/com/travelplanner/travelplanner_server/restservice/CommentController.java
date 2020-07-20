package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.exception.EmptyCommentException;
import com.travelplanner.travelplanner_server.exception.FailedAuthenticationException;
import com.travelplanner.travelplanner_server.exception.NoCommentFoundException;
import com.travelplanner.travelplanner_server.model.Comment;
import com.travelplanner.travelplanner_server.model.User;
import com.travelplanner.travelplanner_server.mongodb.dal.CommentDAL;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.restservice.payload.CommentRequest;
import com.travelplanner.travelplanner_server.restservice.payload.DeleteCommentRequest;
import com.travelplanner.travelplanner_server.restservice.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private final CommentDAL commentDAL;
    private final UserDAL userDAL;
    private int idCount = 0;

    public CommentController(CommentDAL commentDAL, UserDAL userDAL) {
        this.commentDAL = commentDAL;
        this.userDAL = userDAL;
    }

    /**
     * Create a new user comment
     * @param commentRequest
     * JSON example:
     * Request:
     * {
     *   "username": "testUser",
     *   "place_id": "ChIJE9on3F3HwoAR9AhGJW_fL-I",
     *   "content": "It's a great place. I've been there many times."
     * }
     * Response:
     * 200
     */
    @RequestMapping(method = RequestMethod.POST)
    void post(@RequestBody CommentRequest commentRequest) {
        if (commentRequest.getContent() == null || commentRequest.getContent().length() == 0) {
            throw new EmptyCommentException();
        }
        Comment comment = Comment.builder()
                .username(commentRequest.getUsername())
                .place_id(commentRequest.getPlace_id())
                .content(commentRequest.getContent())
                .id(String.valueOf(idCount++))
                .createTime("dateCreated")
                .build();
        commentDAL.createComment(comment);
    }

    /**
     * Delete an user comment
     * @param deleteCommentRequest
     * JSON example:
     * Request:
     * {
     *   "comment_id": "0"
     * }
     * Response:
     * 200
     */
    @RequestMapping(method = RequestMethod.DELETE)
    void delete(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        // 1. token verification (now is verified by if(user == null))
        // 2. handle delete comment exception
        try {
            commentDAL.deleteComment(deleteCommentRequest.getComment_id());
        } catch (NoCommentFoundException noCommentFoundException){
            throw noCommentFoundException;
        }
    }
}
