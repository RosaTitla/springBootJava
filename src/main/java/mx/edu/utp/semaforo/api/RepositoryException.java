package mx.edu.utp.semaforo.api;
import org.springframework.dao.DataAccessException;
public final class RepositoryException extends DataAccessException {
    private RepositoryException(final String msg) {
        super(msg);
    }

    public static DataAccessException create(final String msg) {
        return new RepositoryException(msg);
    }
}