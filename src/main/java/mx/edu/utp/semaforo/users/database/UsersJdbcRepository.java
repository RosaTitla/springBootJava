package mx.edu.utp.semaforo.users.database;

import mx.edu.utp.semaforo.users.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class UsersJdbcRepository implements UsersRepository{
    private final JdbcTemplate template;
    private final UnaryOperator<String> passwordEncoder;

    private UsersJdbcRepository(final JdbcTemplate template, final UnaryOperator<String> passwordEncoder) {
        this.template = template;
        this.passwordEncoder = passwordEncoder;
    }

    public static UsersJdbcRepository create(final JdbcTemplate template, final UnaryOperator<String> passwordEncoder) {
        return new UsersJdbcRepository(template, passwordEncoder);
    }


    @Override
    public boolean createUser(User user) {
        final Predicate<User> createUser = CreateUser.create(template, passwordEncoder);
        return createUser.test(user);
    }

    @Override
    public boolean userExistsWithUsername(String username) {
        final Predicate<String> userExists = UserExistsWithUsername.create(template);
        return userExists.test(username);
    }

    @Override
    public List<User> getAllUsers() {
        final Function<Void, List<User>> getAllUsers = GetAllUsers.create(template);
        return getAllUsers.apply(null);

    }
}
