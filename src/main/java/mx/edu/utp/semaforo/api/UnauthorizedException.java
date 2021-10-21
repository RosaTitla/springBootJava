package mx.edu.utp.semaforo.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{
    //private static final long serialVersionUID=-322219802664L;


    private UnauthorizedException(final String message) {
        super(message);
    }

    public static UnauthorizedException create(final String message) {
        return new UnauthorizedException(message);
    }
}
