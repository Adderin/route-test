package cz.test.routing.service;

import cz.test.routing.api.response.RoutePathResponse;
import cz.test.routing.cache.CountryBordersCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final CountryBordersCache countryBordersCache;

    @Autowired
    public RoutingService(CountryBordersCache countryBordersCache) {
        this.countryBordersCache = countryBordersCache;
    }

    public RoutePathResponse findOptimalRoutePath(String origin, String destination) {
        if (countryBordersCache.requireCacheUpdate()) {
            countryBordersCache.updateCache();
        }
        final Node start = new Node(origin);
        final Node end = new Node(destination);
        return new ShortestPath(start, end).searchForRoute();
    }

    static class Node {
        String name;
        List<Node> neighbors;
        boolean visited = false;
        Node prev = null;

        Node(String name) {
            this.name = name;
            this.neighbors = new ArrayList<>();
        }

        public String toString() {
            return this.name;
        }
    }

    class ShortestPath {
        Node start, end;

        ShortestPath(Node start, Node end) {
            this.start = start;
            this.end = end;
        }

        public RoutePathResponse searchForRoute() {
            Queue<Node> queue = new LinkedList<>();

            start.visited = true;
            queue.add(start);

            while (!queue.isEmpty()) {

                Node currentNode = queue.poll();
                currentNode.neighbors = countryBordersCache.getAdjList().get(currentNode.name).stream().map(Node::new).collect(Collectors.toList());
                for (Node node : currentNode.neighbors) {
                    boolean isVisited = queue.stream().anyMatch(n -> n.name.equals(currentNode.name));
                    if (!isVisited) {
                        node.visited = true;
                        queue.add(node);
                        node.prev = currentNode;
                        if (node.name.equals(end.name)) {
                            queue.clear();
                            return traceRoute(node);
                        }
                    }
                }
            }
            return new RoutePathResponse();
        }

        private RoutePathResponse traceRoute(Node node) {
            RoutePathResponse routePathResponse = new RoutePathResponse();
            List<String> route = new ArrayList<>();

            while (node != null) {
                route.add(node.name);
                node = node.prev;
            }
            Collections.reverse(route);
            routePathResponse.setRoute(route);
            System.out.println(routePathResponse.getRoute());
            return routePathResponse;
        }
    }
}