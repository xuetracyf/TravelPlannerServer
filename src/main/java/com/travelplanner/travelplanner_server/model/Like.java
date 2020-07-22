package com.travelplanner.travelplanner_server.model;

import java.util.List;
import java.util.Map;

public class Like {

    /**
     * given a place id, you can get that place's like count
     * Map<String placeId, Integer likeCount>
     */
    private Map<String, Integer> placeLikesInfo;
    /**
     * given a userid, you can get a list of user's liked places
     * Map<String userId, List<String placeId>> likeInfo
     */
    private Map<String, List<String>> userPlaceLikeInfo;

}
