package mx.edu.utp.semaforo.login.repository;

import mx.edu.utp.semaforo.security.JwtService;
import mx.edu.utp.semaforo.users.database.GetUserWithUsername;
import mx.edu.utp.semaforo.users.database.UserExistsWithUsername;
import mx.edu.utp.semaforo.users.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class LoginJdbcRepository  implements LoginRepository {
    private final JdbcTemplate template;
    private final JwtService jwtService;
    private final BiPredicate<? super String, ? super String> passwordComparator;


    private LoginJdbcRepository(final JdbcTemplate template,
                                final JwtService jwtService,
                                final BiPredicate<? super String, ? super String> passwordComparator) {
        this.template = template;
        this.jwtService = jwtService;
        this.passwordComparator = passwordComparator;
    }

    public static LoginRepository create(
            final JdbcTemplate template,
            final JwtService jwtService,
            final BiPredicate<? super String, ? super String> passwordComparator) {
        return new LoginJdbcRepository(template, jwtService, passwordComparator);
    }


    @Override
    public boolean userExistsWithUsername(final String username) {
        final Predicate<String> userExistWithUsername= UserExistsWithUsername.create(template);
        return userExistWithUsername.test(username);
    }

    @Override
    public boolean matchCredentials(final User user) {
        final Predicate<User> matchCredentials= MatchCredentials.create(template,passwordComparator);
        return matchCredentials.test(user);
    }

    @Override
    public String getToken(User user) {
        return jwtService.getToken(user);
    }

    @Override
    public User getUserWithUsername(final String username) {

        final Function<String,User> getUserWithUsername= GetUserWithUsername.create(template);
        return  getUserWithUsername.apply(username);

    }

}
