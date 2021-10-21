package mx.edu.utp.semaforo.utils;

@FunctionalInterface
public interface Mapper <I,O>{
    O from(I param);
}
