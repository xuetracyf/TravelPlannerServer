package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.model.Plan;
import com.travelplanner.travelplanner_server.mongodb.dal.PlanDAL;
import com.travelplanner.travelplanner_server.restservice.payload.GetPlanResponse;
import com.travelplanner.travelplanner_server.restservice.payload.PlanRequest;
import com.travelplanner.travelplanner_server.restservice.payload.PlanResponse;
import com.travelplanner.travelplanner_server.restservice.payload.PlansData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class PlanController {
    private final PlanDAL planDAL;

    @Autowired
    public PlanController(PlanDAL planDAL) {
        this.planDAL = planDAL;
    }

    @PutMapping(value = "/plan",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Plan> updatePlan(@RequestBody PlanRequest planRequest) {
        Plan plan = planDAL.findPlanByUserIdAndName(planRequest.getUser_id(),planRequest.getName());
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
                    .user_id(planRequest.getUser_id())
                    .build();
            planDAL.createPlan(plan);
        }

        return ResponseEntity.ok().body(plan);
    }

    @GetMapping(value = "/plans",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPlanResponse> getPlan() {
        List<Plan> planList = planDAL.findPlansByUserId("asd111");
        GetPlanResponse getPlanResponse = new GetPlanResponse("ok",new PlansData(planList));
        return ResponseEntity.ok().body(getPlanResponse);
    }
}





