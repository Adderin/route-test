package cz.test.routing.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "RoutePathResponse", description = "Routing response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutePathResponse {

    @JsonFormat
    @Schema(description = "route", example = "CZE, AUT, ITA")
    List<String> route;

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
