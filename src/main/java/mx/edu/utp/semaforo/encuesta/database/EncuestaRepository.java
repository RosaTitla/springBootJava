package mx.edu.utp.semaforo.encuesta.database;

import mx.edu.utp.semaforo.encuesta.domain.Encuesta;

public interface EncuestaRepository {
    boolean newEncuesta(Encuesta encuesta);

    boolean studentExistWithId(String matricula);
}
