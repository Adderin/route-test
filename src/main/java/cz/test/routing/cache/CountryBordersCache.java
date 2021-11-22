package cz.test.routing.cache;

import cz.test.routing.cache.client.CountrySourceClient;
import cz.test.routing.cache.model.CountryBorders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toMap;

@Component
public class CountryBordersCache implements Cacheable {

    private final CountrySourceClient countrySourceClient;
    private Map<String, List<String>> adjList = new HashMap<>();

    @Autowired
    public CountryBordersCache(CountrySourceClient countrySourceClient) {
        this.countrySourceClient = countrySourceClient;
    }

    public void createGraph(final Map<String, List<String>> map) {
        List<String> vertices = new ArrayList<>(map.keySet());
        for (String vertex : vertices) {
            List<String> list = new LinkedList<>();
            adjList.put(vertex, list);
            addEdges(vertex, map.get(vertex));
        }
    }

    public void addEdges(String node, List<String> subEdges) {
        List<String> list = new ArrayList<>(subEdges);
        adjList.put(node, list);
    }

    public Map<String, List<String>> getAdjList() {
        return adjList;
    }

    @Override
    public boolean requireCacheUpdate() {
        return adjList.isEmpty();
    }

    @Override
    public void updateCache() {
        if (requireCacheUpdate()) {
            var countryBorders = countrySourceClient.getCountryBorders();
            final Map<String, List<String>> routeMap = countryBorders.stream()
                .collect(toMap(CountryBorders::getCountry, CountryBorders::getBorders));
            createGraph(routeMap);
        }
    }
}
