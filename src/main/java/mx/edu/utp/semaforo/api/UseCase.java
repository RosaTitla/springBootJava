package mx.edu.utp.semaforo.api;

@FunctionalInterface
public interface UseCase <I,O>{
    O execute(I param);
}
