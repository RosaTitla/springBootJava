package mx.edu.utp.semaforo.login.config;

import mx.edu.utp.semaforo.login.repository.LoginJdbcRepository;
import mx.edu.utp.semaforo.login.repository.LoginRepository;
import mx.edu.utp.semaforo.login.usecase.AuthenticateUser;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.security.JwtService;
import mx.edu.utp.semaforo.security.JwtServiceImpl;
import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.utils.PasswordComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.function.BiPredicate;

@Configuration
public class LoginConfig {
    @Autowired
    private DataSource dataSource;
    private LoginRepository repository;

    @PostConstruct
    public void postConstruct(){
        final JdbcTemplate template=new JdbcTemplate(dataSource);
        final JwtService jwtService= JwtServiceImpl.create();
        final BiPredicate<String,String> passwordComparator= PasswordComparator.create();
        repository= LoginJdbcRepository.create(template, jwtService,passwordComparator);

    }

    @Bean
    public UseCase<User, User> authenticateUser(){
        return  AuthenticateUser.create(repository);

    }
}
