package mx.edu.utp.semaforo.users.database;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.Predicate;

public class UserExistsWithUsername implements Predicate<String> {
    private static final String QUERY="Select count(*) FROM semaforoDB.usuarios WHERE username=?;";
    private final JdbcTemplate template;

    public static Predicate<String> create(final JdbcTemplate template){
        return new UserExistsWithUsername(template);
    }

    private UserExistsWithUsername(final JdbcTemplate template){
        this.template=template;
    }

    @Override
    public boolean test(String username) {
        final Integer numberOfResults =template.queryForObject(
                QUERY,
                Integer.class,
                username
        );
        return numberOfResults>=1;
    }
}
