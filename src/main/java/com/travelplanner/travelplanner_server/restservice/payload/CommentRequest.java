package com.travelplanner.travelplanner_server.restservice.payload;

import lombok.Getter;

@Getter
public class CommentRequest {

    private String username;
    private String place_id;
    private String content;
    // 'comment_id' is required for GET, DELETE
    private String comment_id;
    // 'token' is required for DELETE
    private String token;
}



