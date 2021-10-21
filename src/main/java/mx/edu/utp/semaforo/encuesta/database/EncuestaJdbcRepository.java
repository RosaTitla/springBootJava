package mx.edu.utp.semaforo.encuesta.database;

import mx.edu.utp.semaforo.encuesta.domain.Encuesta;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.ref.PhantomReference;
import java.util.function.Predicate;

public class EncuestaJdbcRepository implements EncuestaRepository{
    private final JdbcTemplate template;

    private EncuestaJdbcRepository(final JdbcTemplate template) {
        this.template = template;
    }

    public static EncuestaJdbcRepository create(final JdbcTemplate template) {
        return new EncuestaJdbcRepository(template);
    }


    @Override
    public boolean newEncuesta(final Encuesta encuesta) {
        final Predicate<Encuesta> newEncuesta=NewEncuesta.create(template);
        return newEncuesta.test(encuesta);
    }

    @Override
    public boolean studentExistWithId(String matricula) {
        return true;
    }
}
