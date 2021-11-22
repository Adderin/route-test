package cz.test.routing.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Schema(name = "RoutingResponse", description = "Routing response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutingResponse {

    @JsonFormat
    @Schema(description = "route", example = "CZE, AUT, ITA")
    List<String> route;

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingResponse that = (RoutingResponse) o;
        return Objects.equals(route, that.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route);
    }
}
