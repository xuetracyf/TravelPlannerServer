package com.travelplanner.travelplanner_server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;



@Document(collection = "plan")
@Builder
@Getter
@Setter
public class Plan {
    @Id
    private String id;
    private String user_id;
    private List<String> place_id;
    private String name;
    private Date createdAt;
    private Date updatedAt;

}
