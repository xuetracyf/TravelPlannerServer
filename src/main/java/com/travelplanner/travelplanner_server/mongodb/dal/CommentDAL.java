package com.travelplanner.travelplanner_server.mongodb.dal;

import com.travelplanner.travelplanner_server.model.Comment;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDAL {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public CommentDAL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Create a Comment instance and store it to mongoDB
     *
     * @param comment
     * @return a new instance of comment
     */
    public void createComment(Comment comment) {
        mongoTemplate.insert(comment);
    }
    public Comment deleteComment(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("comment_id").is(id));
        return mongoTemplate.findAndRemove(query, Comment.class);
    }

    /**
     * Get a single comment
     *
     * @param id comment_id
     * @return comment corresponding to the id
     */
    public Comment getComment(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("comment_id").is(id));
        return mongoTemplate.findOne(query, Comment.class);
    }

    /**
     * Get all comments
     * @return list of existing comments
     */
    public List<Comment> getAllComment() {

        return mongoTemplate.findAll(Comment.class, "comment");
    }
}
