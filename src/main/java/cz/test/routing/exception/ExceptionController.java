package cz.test.routing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionController {

    @GetMapping("")
    public String caughtException() {
        throw new RoutingException("The HTTP Status will be BAD REQUEST (CODE 400)\n");
    }
}
