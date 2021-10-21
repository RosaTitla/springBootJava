package mx.edu.utp.semaforo.login.repository;

import mx.edu.utp.semaforo.users.domain.User;

public interface LoginRepository {
    boolean userExistsWithUsername(String userName);

    boolean matchCredentials(User user);

    String getToken(User user);

    User getUserWithUsername(String username);
}
