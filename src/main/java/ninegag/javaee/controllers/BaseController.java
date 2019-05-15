package ninegag.javaee.controllers;

import ninegag.javaee.util.ResponseMsg;
import ninegag.javaee.util.exceptions.AlreadyLoggedException;
import ninegag.javaee.util.exceptions.BaseException;
import ninegag.javaee.util.exceptions.InvalidInputException;
import ninegag.javaee.util.exceptions.NotLoggedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public abstract class BaseController {
    @ExceptionHandler({InvalidInputException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMsg handleInputExceptions(Exception e){
        return new ResponseMsg(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }
    @ExceptionHandler({AlreadyLoggedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseMsg handleLoggedException(Exception e){
        return new ResponseMsg(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
    }
    @ExceptionHandler({NotLoggedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseMsg handleNotLoggedException(Exception e){
        return new ResponseMsg(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
    }
    @ExceptionHandler({BaseException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMsg handleBaseExceptions(Exception e){
        return new ResponseMsg(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMsg handleServerExceptions(Exception e){
        return new ResponseMsg(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
    }

}
