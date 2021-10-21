package mx.edu.utp.semaforo.login.usecase;

import mx.edu.utp.semaforo.login.repository.LoginRepository;
import mx.edu.utp.semaforo.api.UnauthorizedException;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.users.domain.UserImpl;

import java.util.Objects;

public class AuthenticateUser implements UseCase<User,User> {
    private final LoginRepository repository;

    private User user;

    private AuthenticateUser(final LoginRepository repository) {
        this.repository = repository;
    }

    public static UseCase<User, User> create(LoginRepository repository) {
        return new AuthenticateUser(repository);
    }

    @Override
    public User execute(final User user) {
        setUser(user);
        verifyIfUserExists();
        matchCredentials();
        getUser();
        return getUserWithToken();
    }
    private void setUser(final User user) {

        this.user= Objects.requireNonNull(user,"usuario vacio");
    }

    private User getUserWithToken() {
        final String token =repository.getToken(user);

        return new UserImpl.Builder()
                .from(user)
                .token(token)
                .build();

    }

    private void getUser() {
        user=repository.getUserWithUsername(getUsername());

    }

    private String getUsername() {
        return user.getUsername();
    }

    private void matchCredentials() {
        boolean match=repository.matchCredentials(user);
        if(!match){
            throwUnauthorizedException();
        }
    }

    private void verifyIfUserExists() {
        final boolean exists=repository.userExistsWithUsername(getUsername());
        if (!exists){
            throwUnauthorizedException();
            //throw  UnauthorizedException.create(getFailureMessage())
        }

    }

    private void throwUnauthorizedException() {
        throw  UnauthorizedException.create(getFailureMessage());
    }

    private String getFailureMessage() {
        return "usuario o password incorrectos";
    }


}
