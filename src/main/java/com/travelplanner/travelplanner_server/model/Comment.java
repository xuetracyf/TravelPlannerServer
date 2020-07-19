package com.travelplanner.travelplanner_server.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
<<<<<<< HEAD
=======
import org.springframework.data.mongodb.core.index.Indexed;
>>>>>>> fa059b6... basic comment API + test done
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
<<<<<<< HEAD

}

=======
    private String modifyTime;
}
>>>>>>> fa059b6... basic comment API + test done
