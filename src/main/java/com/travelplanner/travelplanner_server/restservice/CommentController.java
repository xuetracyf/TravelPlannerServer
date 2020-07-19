package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.exception.EmptyCommentException;
import com.travelplanner.travelplanner_server.model.Comment;
import com.travelplanner.travelplanner_server.mongodb.dal.CommentDAL;
import com.travelplanner.travelplanner_server.restservice.payload.CommentRequest;
import com.travelplanner.travelplanner_server.restservice.payload.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private final CommentDAL commentDAL;
    private int idCount = 0;

    public CommentController(CommentDAL commentDAL) {
        this.commentDAL = commentDAL;
    }

    /**
     * Create a new user comment.
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
        // if (commentRequest.getPlace_id() == null || )

        Comment comment = Comment.builder()
                .username(commentRequest.getUsername())
                .place_id(commentRequest.getPlace_id())
                .content(commentRequest.getContent())
                .id(String.valueOf(idCount))
                .createTime("dateCreated")
                .build();
        commentDAL.createComment(comment);
        CommentResponse commentResponse = new CommentResponse(String.valueOf(idCount));
        idCount++;
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

