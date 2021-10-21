package mx.edu.utp.semaforo.users.database;

import mx.edu.utp.semaforo.users.domain.User;

import java.util.List;

public interface UsersRepository {
    boolean createUser(User user);
    boolean userExistsWithUsername(String username);
    List<User> getAllUsers();
}
