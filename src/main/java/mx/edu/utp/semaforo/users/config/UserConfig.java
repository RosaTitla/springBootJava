package mx.edu.utp.semaforo.users.config;

import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.users.database.UsersJdbcRepository;
import mx.edu.utp.semaforo.users.database.UsersRepository;
import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.users.usecase.CreateUser;
import mx.edu.utp.semaforo.users.usecase.GetAllUsers;
import mx.edu.utp.semaforo.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.function.UnaryOperator;

public class UserConfig {
    @Autowired
    private DataSource dataSource;

    private UsersRepository repository;

    @PostConstruct
    public void postConstruct() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        final UnaryOperator<String> passwordEncoder = PasswordEncoder.create();
        repository= UsersJdbcRepository.create(jdbcTemplate, passwordEncoder);
    }

    @Bean
    public UseCase<User, String> createUser() {
        return CreateUser.create(repository);
    }

    @Bean
    public UseCase<Void, List<User>> getAllUsers() {
        return GetAllUsers.create(repository);
    }
}
