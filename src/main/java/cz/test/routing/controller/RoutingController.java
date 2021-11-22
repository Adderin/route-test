package cz.test.routing.controller;

import cz.test.routing.api.RoutingApi;
import cz.test.routing.api.response.RoutePathResponse;
import cz.test.routing.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoutingController implements RoutingApi {

    private final RoutingService routingService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }


    @Override
    public RoutePathResponse routeCheck(final String origin, final String destination) {
        return routingService.findOptimalRoutePath(origin, destination);
    }
}