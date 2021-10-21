package mx.edu.utp.semaforo.encuesta.database;

import mx.edu.utp.semaforo.encuesta.domain.Encuesta;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.Predicate;

public final class NewEncuesta  implements Predicate<Encuesta> {
    private final JdbcTemplate template;


    private static final String QUERY =
            "INSERT INTO semaforoDB.encuestas " +
                    "(matricula, nombre, timestamp, sintomas, resultados) " +
                    "VALUES(?, ?, ?, ?, ?);";
    private NewEncuesta(final JdbcTemplate template) {
        this.template = template;
    }

    static Predicate<Encuesta> create(final JdbcTemplate template) {
        return new NewEncuesta(template);
    }

    @Override
    public boolean test(final Encuesta encuesta) {
        System.out.println(encuesta);
        final int results = template.update(
                QUERY,
                encuesta.getMatricula(),
                encuesta.getNombre(),
                encuesta.getTimeStamp(),
                encuesta.getSintomas(),
                encuesta.getResultado()
        );
        return results >= 1;
    }

}
