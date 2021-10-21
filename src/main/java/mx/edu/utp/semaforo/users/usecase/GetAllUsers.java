package mx.edu.utp.semaforo.users.usecase;

import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.users.database.UsersRepository;
import mx.edu.utp.semaforo.users.domain.User;

import java.util.Comparator;
import java.util.List;

public class GetAllUsers implements UseCase<Void, List<User>> {
    private final UsersRepository repository;

    private GetAllUsers(final UsersRepository repository) {
        this.repository = repository;
    }

    public static UseCase<Void, List<User>> create(final UsersRepository repository) {
        return new GetAllUsers(repository);
    }

    @Override
    public List<User> execute(Void param) {
        final List<User> users = repository.getAllUsers();
        users.sort(Comparator.comparing(User::getUsername));
        return users;
    }
}
