package mx.edu.utp.semaforo.users.database;

import mx.edu.utp.semaforo.users.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CreateUser implements Predicate<User> {
    public static final String QUERY = "INSERT INTO semaforoDB.usuarios " +
            "(username, password, nombre, apellidoPaterno, apellidoMaterno, email, timestamp, idRol) " +
            "VALUES(?,?, ?, ?, ?, ?,?,?);";
    private final JdbcTemplate template;
    private final UnaryOperator<String> passwordEncoder;

    public static Predicate<User> create(
            final JdbcTemplate template,
            final UnaryOperator<String> passwordEncoder) {
        return new CreateUser(template, passwordEncoder);
    }

    private CreateUser(final JdbcTemplate template, final UnaryOperator<String> passwordEncoder) {
        this.template = template;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public boolean test(final User user) {
        final String password = user.getPassword();
        final String hash = passwordEncoder.apply(password);

        final int result = template.update(
                QUERY,
                user.getUsername(),
                hash,
                user.getFirstName(),
                user.getFathersLastName(),
                user.getMothersLastName(),
                user.getEmail(),
                user.getTimeStamp(),
                user.getIdRole()
        );
        return result >= 1;
    }
}
