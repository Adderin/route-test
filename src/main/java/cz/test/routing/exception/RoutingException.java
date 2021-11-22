package cz.test.routing.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoutingException extends RuntimeException {

    public RoutingException(String message) {
        super(message);
    }
}
