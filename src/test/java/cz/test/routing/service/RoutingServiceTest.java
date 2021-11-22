package cz.test.routing.service;

import cz.test.routing.api.response.RoutingResponse;
import cz.test.routing.cache.CountryCache;
import cz.test.routing.exception.RoutingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoutingServiceTest {

    @Mock
    private CountryCache countryCache;

    @InjectMocks
    private RoutingService service;

    final Map<String, List<String>> testCountryMap = new HashMap<>();
    final Map<String, Map<String, List<String>>> testRegionsMap = new HashMap<>();
    final List<String> route = new ArrayList<>();


    @BeforeEach
    public void setup() {
        testCountryMap.put("CZE", List.of("AUT", "DEU", "POL", "SLK"));
        testCountryMap.put("AUT", List.of("ITA", "DEU", "HUN", "SLV", "CZE"));
        testCountryMap.put("ITA", List.of("HUN", "SLV", "AUT"));
        testCountryMap.put("USA", List.of("CAN", "MEX"));

        testRegionsMap.put("Europe", testCountryMap);

        route.addAll(List.of("CZE", "AUT", "ITA"));
        when(countryCache.getCountryGraph()).thenReturn(testCountryMap);
        when(countryCache.getRegionsMap()).thenReturn(testRegionsMap);
    }

    @Test
    public void shouldReturnCorrectRoute() {
        // Arrange
        final RoutingResponse routingResponse = new RoutingResponse();
        routingResponse.setRoute(route);

        // Act
        final RoutingResponse response = service.findOptimalRoutePath("CZE", "ITA");

        // Assert
        assertEquals(routingResponse, response);
    }

    @Test
    public void shouldFailWithRoutingExceptionIfNoConnection() {
        // Act + Assert
        assertThrows(RoutingException.class, () -> service.findOptimalRoutePath("CZE", "BRA"));
    }
}