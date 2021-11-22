package cz.test.routing.api;

import cz.test.routing.api.response.RoutePathResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@OpenAPIDefinition(
        info = @Info(
                title = "Routing-Service",
                version = "0.0.1-SNAPSHOT",
                contact = @Contact(url = "http://localhost:8181/")
        )
)
@RestController
@RequestMapping(value = "/", produces = {APPLICATION_JSON_VALUE})
public interface RoutingApi {

    @GetMapping("routing/{origin}/{destination}")
    @Operation(summary = "Create a route if possible",
            responses = {@ApiResponse(description = "RoutingResponse",
                    headers = {@Header(name = "duration", description = "Time in milliseconds the processing took from request to response",
                            schema = @Schema(allOf = Long.class))
                    })
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routing successful"),
            @ApiResponse(responseCode = "400", description = "No route found"),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    RoutePathResponse routeCheck(@PathVariable String origin, @PathVariable String destination);
}
