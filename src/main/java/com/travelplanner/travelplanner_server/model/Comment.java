package com.travelplanner.travelplanner_server.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comment")
@Builder
@Getter
public class Comment {
    @Id
    private String id;
    private String username;
    private String place_id;
    private String content;
    private String createTime;

}


