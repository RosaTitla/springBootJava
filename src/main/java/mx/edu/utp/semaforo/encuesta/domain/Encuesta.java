package mx.edu.utp.semaforo.encuesta.domain;

public interface Encuesta {
    int getIdEncuesta();
    String getMatricula();
    String getNombre();
    long getTimeStamp();
    String getFecha();
    String getSintomas();
    String getResultado();

}
