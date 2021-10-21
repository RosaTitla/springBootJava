package mx.edu.utp.semaforo.encuesta.config;



import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.encuesta.usecase.createEncuesta;
import mx.edu.utp.semaforo.encuesta.database.EncuestaJdbcRepository;
import mx.edu.utp.semaforo.encuesta.database.EncuestaRepository;
import mx.edu.utp.semaforo.encuesta.domain.Encuesta;
import mx.edu.utp.semaforo.encuesta.domain.EncuestaMapper;
import mx.edu.utp.semaforo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class EncuestaConfig {

    private EncuestaRepository repository;
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void postConstruct() {
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        repository = EncuestaJdbcRepository.create(template);
    }

    @Bean
    public UseCase<Encuesta, String> createUser() {
        return createEncuesta.create(repository);
    }

    @Bean
    public static Mapper<String, Encuesta> encuestaMapper() {
        return EncuestaMapper.create();
    }
}
