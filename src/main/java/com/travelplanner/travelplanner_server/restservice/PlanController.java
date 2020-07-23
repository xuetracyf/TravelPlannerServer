package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.model.Plan;
import com.travelplanner.travelplanner_server.model.User;
import com.travelplanner.travelplanner_server.mongodb.dal.PlanDAL;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.restservice.config.JwtTokenUtil;
import com.travelplanner.travelplanner_server.restservice.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class PlanController {
    @Autowired
    private PlanDAL planDAL;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDAL userDAL;


    @PutMapping(value = "/plan",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Plan> updatePlan(@RequestBody PlanRequest planRequest,HttpServletRequest request) {
        String token = null;
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userDAL.findUserByUsername(username);
        Plan plan = planDAL.findPlanByUserAndName(user,planRequest.getName());
        if (plan != null) {
            planDAL.updatePlan(plan,planRequest.getPlace_id(),new Date());
            plan.setPlace_id(planRequest.getPlace_id());
            plan.setUpdatedAt(new Date());
        }
        else {

            plan = Plan.builder()
                    .name(planRequest.getName())
                    .place_id(planRequest.getPlace_id())
                    .updatedAt(new Date())
                    .createdAt(new Date())
                    .user(user)
                    .build();
            planDAL.createPlan(plan);
        }

        return ResponseEntity.ok().body(plan);
    }

    @GetMapping(value = "/plans",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPlanResponse> getPlan(HttpServletRequest request) {
        String token = null;
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userDAL.findUserByUsername(username);
        List<Plan> planList = planDAL.findPlansByUser(user);
        GetPlanResponse getPlanResponse = new GetPlanResponse("ok",new PlansData(planList));
        return ResponseEntity.ok().body(getPlanResponse);
    }
}





