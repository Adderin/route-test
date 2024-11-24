package cz.test.routing.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;

import java.util.List;

@Schema(name = "RoutingResponse", description = "Routing response")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class RoutingResponse {

    @JsonFormat
    @Schema(description = "route", example = "CZE, AUT, ITA")
    List<String> route;

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
