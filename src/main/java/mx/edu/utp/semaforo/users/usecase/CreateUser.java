package mx.edu.utp.semaforo.users.usecase;

import mx.edu.utp.semaforo.api.RepositoryException;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.users.database.UsersRepository;
import mx.edu.utp.semaforo.users.domain.User;

public class CreateUser implements UseCase<User, String> {
    private final UsersRepository repository;
    private User user;

    private CreateUser(final UsersRepository repository) {
        this.repository = repository;

    }

    public static UseCase<User, String> create(final UsersRepository repository) {
        return new CreateUser(repository);
    }

    @Override
    public String execute(final User user) {
        setUser(user);
        verifyIfUsernameIsRepeated();
        insertUserInDb();
        return getSuccessMessage();
    }
    private void setUser(final User user) {
        this.user = user;
    }

    private void verifyIfUsernameIsRepeated() {
        final boolean exists = repository.userExistsWithUsername(getUsername());

        if (!exists) {
            throw new IllegalArgumentException(getRepeatedUsernameWarning());
        }
    }

    private String getRepeatedUsernameWarning() {
        return String.format(
                "El nombre del usuario %s ya se encuentra registrado",
                getUsername()
        );
    }

    private String getUsername() {
        return user.getUsername();
    }

    private void insertUserInDb() {
        final boolean success = repository.createUser(user);

        if (!success) {
            throw RepositoryException.create(getFailureWarning());
        }
    }

    private String getFailureWarning() {
        return String.format(
                "Hubo un problema al tratar de dar de alta al usuario %s",
                getUsername()
        );
    }



    private String getSuccessMessage() {
        return String.format("Se insertó con exito el usuario %s", getUsername());
    }
}


