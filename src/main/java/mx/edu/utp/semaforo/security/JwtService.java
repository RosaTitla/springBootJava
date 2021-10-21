package mx.edu.utp.semaforo.security;

import mx.edu.utp.semaforo.users.domain.User;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String getToken(User user);

    Authentication getAuthorization(String token);
}
