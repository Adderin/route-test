package cz.test.routing.cache;

import cz.test.routing.cache.client.CountrySourceClient;
import cz.test.routing.cache.model.Country;
import cz.test.routing.cache.model.RegionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
@CacheConfig(cacheNames={"requireCacheUpdate", "updateCache" })
public class CountryCache {

    private static final String LONELY_COUNTRY = "LonelyCountry";
    private final CountrySourceClient countrySourceClient;
    private Map<String, List<String>> countryGraph = new HashMap<>();
    private final Map<String, Map<String, List<String>>> regionsMap = new HashMap<>();


    @Autowired
    public CountryCache(final CountrySourceClient countrySourceClient) {
        this.countrySourceClient = countrySourceClient;
    }

    public Map<String, List<String>> getCountryGraph() {
        return countryGraph;
    }

    @Cacheable
    public boolean requireCacheUpdate() {
        return regionsMap.isEmpty() && countryGraph.isEmpty();
    }

    @Cacheable
    public void updateCache() {
        if (requireCacheUpdate()) {
            var countryBorders = countrySourceClient.getCountryList();

            for (Country country : countryBorders) {

                if (!country.getBorders().isEmpty()) {
                    if (RegionEnum.EURASIA.getRegions().contains(country.getRegion())) {
                        regionsMap.computeIfAbsent(RegionEnum.EURASIA.toString(), k -> new HashMap<>()).put(country.getCountry(), country.getBorders());
                    } else {
                        regionsMap.computeIfAbsent(RegionEnum.AMERICA.toString(), k -> new HashMap<>()).put(country.getCountry(), country.getBorders());
                    }
                } else {
                    regionsMap.computeIfAbsent(LONELY_COUNTRY, k -> new HashMap<>()).put(country.getCountry(), country.getBorders());
                }
            }
            countryGraph = countryBorders.stream()
                    .collect(toMap(Country::getCountry, Country::getBorders));
        }
    }

    public Map<String, Map<String, List<String>>> getRegionsMap() {
        return regionsMap;
    }
}
