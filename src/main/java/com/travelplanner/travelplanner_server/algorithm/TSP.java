package com.travelplanner.travelplanner_server.algorithm;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.graph.ValueGraph;

import java.util.*;
import java.util.stream.Collectors;

public class TSP {
    /*
     * Implement TSP using dynamic programming
     * C(S, i) be the cost of the minimum cost path visiting each vertex in set S exactly once,
     *      starting at 1 and ending at i.
     * If size of S is 2, then S must be {1, i}, C(S, i) = dist(1, i)
     * Else if size of S is greater than 2:
     *      C(S, i) = min { C(S-{i}, j) + dis(j, i)} where j belongs to S, j != i and j != 1.
     * Time: O(n^2*2^n)
     */
    public static RouteCost findShortestPath(ValueGraph<String, Long> distGraph) {
        Set<String> nodes = new HashSet<>(distGraph.nodes());
        nodes.remove("startPosition");
        // Store the cost of every route
        Map<Route, RouteCost> cost = new HashMap<>();
        // Base case
        for (String place: nodes) {
            Route route = new Route(Sets.newHashSet(place), place);
            RouteCost routeCost = new RouteCost(distGraph.edgeValue("startPosition", place).get(),
                    Lists.newArrayList(place));
            cost.put(route, routeCost);
        }
        // Generate all subSets of places with size >= 2
        List<Set<String>> allSubset = findAllSubSet(nodes);
        for (int size = 2; size <= nodes.size(); size++) {
            Map<Route, RouteCost> newCost = new HashMap<>();
            final int finalSize = size;
            List<Set<String>> allSubsetWithSize = allSubset.stream().filter(placeSet -> placeSet.size() == finalSize)
                    .collect(Collectors.toList());
            allSubset.removeIf(placeSet -> placeSet.size() == finalSize); // Remove it when we finish filter the placeSet
            for (Set<String> newPlaceSet: allSubsetWithSize) {
                for (String place_i: newPlaceSet) {
                    Long min = Long.MAX_VALUE;
                    List<String> visitOrder = new ArrayList<>();
                    Set<String> placeSetWithout_i = new HashSet<>(newPlaceSet);
                    placeSetWithout_i.remove(place_i); // Remove place_i to provide S - {i}
                    for (String place_j: placeSetWithout_i) {
                        RouteCost rc_ij = cost.get(new Route(placeSetWithout_i, place_j));
                        Long curDist = rc_ij.distance + distGraph.edgeValue(place_j, place_i).get();
                        if (curDist < min) {
                            min = curDist;
                            visitOrder = new ArrayList<>(rc_ij.visitOrder);
                            visitOrder.add(place_i);
                        }
                    }
                    newCost.put(new Route(newPlaceSet, place_i), new RouteCost(min, visitOrder)); // update newCost
                }
            }
            // Update cost map
            cost = newCost;
        }
        // Find the minimum distance for all routes, consider returning to startPosition.
        Long min = Long.MAX_VALUE;
        List<String> visitOrder = new ArrayList<>();
        for (Map.Entry<Route, RouteCost> entry: cost.entrySet()) {
            RouteCost rc = entry.getValue();
            Route route = entry.getKey();
            Long curDist = rc.distance + distGraph.edgeValue(route.endingPlace, "startPosition").get();
            if (curDist < min) {
                min = curDist;
                visitOrder = rc.visitOrder;
            }
        }
        return new RouteCost(min, visitOrder);
    }

    private static List<Set<String>> findAllSubSet(Set<String> placeSet) {
        List<String> places = new ArrayList<>(placeSet);
        List<Set<String>> result = new ArrayList<>();
        Set<String> cur = new HashSet<>();
        findAllSubSet(places, 0, cur, result);
        return result;
    }

    private static void findAllSubSet(List<String> place, int level, Set<String> cur, List<Set<String>> result) {
        if (level == place.size()) {
            if (cur.size() >= 2) {
                result.add(new HashSet<>(cur));
            }
            return;
        }
        cur.add(place.get(level));
        findAllSubSet(place, level+1, cur, result);
        cur.remove(place.get(level));
        findAllSubSet(place, level+1, cur, result);
    }

    public static class RouteCost {
        public Long distance;
        public List<String> visitOrder;

        public RouteCost(Long distance, List<String> visitOrder) {
            this.distance = distance;
            this.visitOrder = visitOrder;
        }
    }

    private static class Route {
        Set<String> placeSet;
        String endingPlace;

        public Route(Set<String> placeSet, String endingPlace) {
            this.placeSet = placeSet;
            this.endingPlace = endingPlace;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Route route = (Route) o;
            return placeSet.equals(route.placeSet) && endingPlace.equals(route.endingPlace);
        }

        @Override
        public int hashCode() {
            return Objects.hash(placeSet, endingPlace);
        }
    }
}
