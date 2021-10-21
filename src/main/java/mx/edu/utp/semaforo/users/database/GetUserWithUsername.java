package mx.edu.utp.semaforo.users.database;

import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.users.domain.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.Function;

public class GetUserWithUsername implements Function<String, User> {
    private static final String QUERY=
            "SELECT * FROM semaforoDB.usuarios_roles_view WHERE username=?";
    private final JdbcTemplate template;

    private GetUserWithUsername(final JdbcTemplate template) {
        this.template = template;
    }

    public static Function<String,User> create(final JdbcTemplate template) {
        return new GetUserWithUsername(template);
    }

    @Override
    public User apply(final String username) {
        return template.queryForObject(
                QUERY,
                UserMapper.create(),
                username
        );

    }
}
