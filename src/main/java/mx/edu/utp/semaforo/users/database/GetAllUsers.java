package mx.edu.utp.semaforo.users.database;

import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.users.domain.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.function.Function;

public class GetAllUsers implements Function<Void, List<User>> {
    private static final String QUERY =
            "SELECT * FROM semaforoDB.usuarios_roles_view;";
    private final JdbcTemplate template;

    private GetAllUsers(final JdbcTemplate template) {
        this.template = template;
    }

    public static Function<Void, List<User>> create(final JdbcTemplate template) {
        return new GetAllUsers(template);
    }

    @Override
    public List<User> apply(Void unused) {
        return template.query(
                QUERY,
                UserMapper.create()
        );
    }
}
