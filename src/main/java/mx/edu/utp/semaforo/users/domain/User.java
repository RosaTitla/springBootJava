package mx.edu.utp.semaforo.users.domain;

public interface User {
    String getUsername();
    String getPassword();
    String getFirstName();
    String getFathersLastName();
    String getMothersLastName();
    String getEmail();
    long getTimeStamp();
    String getFecha();
    int getIdRole();
    String getRole();
    String getToken();
}
