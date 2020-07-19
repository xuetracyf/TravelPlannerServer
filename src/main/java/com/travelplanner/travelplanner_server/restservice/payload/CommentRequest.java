package com.travelplanner.travelplanner_server.restservice.payload;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CommentRequest implements Serializable {

    private String username;
    private String place_id;
    private String content;
    private String comment_id;
}



