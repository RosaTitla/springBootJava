package mx.edu.utp.semaforo.login.repository;

import mx.edu.utp.semaforo.users.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MatchCredentials implements Predicate<User> {
    private static final String QUERY="Select password FROM semaforoDB.usuarios WHERE username=?;";
    private final JdbcTemplate template;
    private final BiPredicate<? super String, ? super String> passwordComparator;

    private MatchCredentials(
            final JdbcTemplate template,
            final BiPredicate<? super String, ? super String> passwordComparator) {
        this.template = template;
        this.passwordComparator = passwordComparator;
    }

    public static Predicate<User> create(
            final JdbcTemplate template,
            final BiPredicate<? super String, ? super String> passwordComparator){
        return new MatchCredentials(template,passwordComparator);
    }




    @Override
    public boolean test(final User user) {
        final String hash=template.queryForObject(
                QUERY,
                String.class,
                user.getUsername()
        );
        return passwordComparator.test(user.getPassword(), hash);
    }
}
