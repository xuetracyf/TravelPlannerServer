package com.travelplanner.travelplanner_server.model;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter

public class Place {
    String name;
    String place_id;
    LatLng location; //com.google.maps.model.LatLng;
    int total_rating;
    List<String> photo_refs;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        return this.place_id.equals(((Place)obj).place_id);
    }

    @Override
    public int hashCode() {
        return place_id.hashCode();
    }

    /*
    * ref:
    * https://www.geodatasource.com/developers/java
    * */

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515 * 1.609344 * 1000;// in meter
            return dist;
        }
    }

    public boolean isInCircle(LatLng center, int radius_in_meter) {
        return distance(center.lat, center.lng, this.location.lat, this.location.lng) <= radius_in_meter;
    }

    //if you are not in polar area, that approximation is make sense.
    public boolean isInRect(LatLng lefttop, LatLng rightdown) {
        if(this.location.lat > lefttop.lat || this.location.lat < rightdown.lat || this.location.lng > rightdown.lng || this.location.lng < this.location.lng) return false;
        return true;
    }

    public static void main(String[] args) {
        Place p = new PlaceBuilder().name("111").place_id("123").build();
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(p));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


