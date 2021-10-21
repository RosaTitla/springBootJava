package mx.edu.utp.semaforo.encuesta.usecase;

import mx.edu.utp.semaforo.api.RepositoryException;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.encuesta.database.EncuestaRepository;
import mx.edu.utp.semaforo.encuesta.domain.Encuesta;

public final class createEncuesta implements UseCase<Encuesta, String> {

    private final EncuestaRepository repository;
    private Encuesta encuesta;

    private createEncuesta(final EncuestaRepository repository) {
        this.repository = repository;
    }


    public static UseCase<Encuesta, String> create(final EncuestaRepository repository) {
        return new createEncuesta(repository);
    }

    @Override
    public String execute(final Encuesta encuesta) {
        setEncuesta(encuesta);
        verifyStudentExist();
        verifyQuestionaryIsDone();
        insertEncuestaInDb();
        return getSuccessMessage();
    }

    private void verifyQuestionaryIsDone() {

    }

    private void verifyStudentExist() {
        final boolean exists = repository.studentExistWithId(encuesta.getMatricula());
        if (!exists) {
            throw RepositoryException.create(getStudentDoesntExistWarning());
        }
    }

    private String getStudentDoesntExistWarning() {
        return "El estudiante con matricula " + encuesta.getMatricula() + " no existe";
    }

    private void insertEncuestaInDb() {
        final boolean success = repository.newEncuesta(encuesta);

        if (!success) {
            throw RepositoryException.create(getFailureWarning());
        }
    }

    private String getFailureWarning() {
        return "Hubo un problema al tratar de dar de alta la encuesta";
    }

    private void setEncuesta(final Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    private String getSuccessMessage() {

        return String.format(
                "Se insertó con exito la encuesta del estudiante %s",
                encuesta.getNombre()
        );
    }

}
