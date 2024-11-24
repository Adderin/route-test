package cz.test.routing.service;

import cz.test.routing.api.response.RoutingResponse;
import cz.test.routing.cache.CountryCache;
import cz.test.routing.exception.RoutingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final CountryCache countryCache;

    @Autowired
    public RoutingService(CountryCache countryCache) {
        this.countryCache = countryCache;
    }

    public RoutingResponse findOptimalRoutePath(final String origin, final String destination) {
        if (countryCache.requireCacheUpdate()) {
            countryCache.updateCache();
        }
        final Node start = new Node(origin);
        final Node end = new Node(destination);
        return new ShortestPath(start, end).filterToRegions();
    }

    static class Node {
        @Getter
        private final String countryName;
        private List<Node> neighbors;
        private boolean visited = false;
        private Node prev = null;

        Node(final String countryName) {
            this.countryName = countryName;
            this.neighbors = new ArrayList<>();
        }

    }

    class ShortestPath {
        Node start, end;

        ShortestPath(final Node start, final Node end) {
            this.start = start;
            this.end = end;
        }

        public RoutingResponse filterToRegions() {
            var regions = countryCache.getRegionsMap();
            var requiredRegion = regions.keySet().stream()
                    .filter(r -> regions.get(r).containsKey(start.getCountryName()))
                    .filter(r -> regions.get(r).containsKey(end.getCountryName()))
                    .findAny();
            if (requiredRegion.isEmpty()) {
                throw new RoutingException("There is no connection between origin and destination");
            }
            return searchForRoute();
        }

        public RoutingResponse searchForRoute() {
            Queue<Node> queue = new LinkedList<>();

            start.visited = true;
            queue.add(start);

            Set<String> visitedCountries = new HashSet<>();
            visitedCountries.add(start.countryName);

            while (!queue.isEmpty()) {

                Node currentNode = queue.poll();
                currentNode.neighbors = countryCache.getCountryGraph().get(currentNode.countryName).stream().map(Node::new).collect(Collectors.toList());
                for (Node node : currentNode.neighbors) {
                    boolean isVisited = queue.stream().anyMatch(n -> n.countryName.equals(currentNode.countryName));
                    if ((!isVisited && !node.visited) || !visitedCountries.contains(node.countryName)) {
                        node.visited = true;
                        queue.add(node);
                        visitedCountries.add(node.countryName);
                        node.prev = currentNode;
                        if (node.countryName.equals(end.countryName)) {
                            queue.clear();
                            return traceRoute(node);
                        }
                    }
                }
            }
            return new RoutingResponse();
        }

        private RoutingResponse traceRoute(Node node) {
            RoutingResponse routingResponse = new RoutingResponse();
            List<String> route = new ArrayList<>();

            while (node != null) {
                route.add(node.countryName);
                node = node.prev;
            }
            Collections.reverse(route);
            routingResponse.setRoute(route);
            return routingResponse;
        }
    }
}