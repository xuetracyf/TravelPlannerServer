package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.exception.EmptyCommentException;
import com.travelplanner.travelplanner_server.model.Comment;
import com.travelplanner.travelplanner_server.mongodb.dal.CommentDAL;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.restservice.payload.CommentRequest;
import com.travelplanner.travelplanner_server.restservice.payload.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentDAL commentDAL;
    @Autowired
    private UserDAL userDAL;



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
    @RequestMapping(value="/comment", method=RequestMethod.POST)
    public ResponseEntity<CommentResponse> post(@RequestBody CommentRequest commentRequest) {

        if (commentRequest.getContent() == null || commentRequest.getContent().length() == 0) {
            throw new EmptyCommentException();
        }

        Comment comment = Comment.builder()
                .username(commentRequest.getUsername())
                .place_id(commentRequest.getPlace_id())
                .content(commentRequest.getContent())
                .createTime("dateCreated")
                .build();
        comment = commentDAL.createComment(comment);
        CommentResponse commentResponse = new CommentResponse(comment.getId());

        return ResponseEntity.ok().body(commentResponse);
    }

    /**
     * Delete an user comment.
     * @param commentRequest
     * JSON example:
     * Request:
     * {
     *   "comment_id": "0"
     * }
     * Response:
     * 200
     */
    @RequestMapping(value="/comment", method=RequestMethod.DELETE)
    public void delete(@RequestBody CommentRequest commentRequest) {
        try {
            commentDAL.deleteComment(commentRequest.getComment_id());
        } catch (EmptyCommentException emptyCommentException) {
            throw emptyCommentException;
        }
    }

}