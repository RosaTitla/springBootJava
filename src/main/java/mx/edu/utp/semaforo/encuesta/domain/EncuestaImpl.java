package mx.edu.utp.semaforo.encuesta.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonDeserialize(builder =EncuestaImpl.Builder.class)
public class EncuestaImpl implements Encuesta{


    private final int idEncuesta;
    private final String matricula;
    private final String nombre;
    private final long timestamp;
    private final String fecha;
    private final String sintomas;
    private final String resultado;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder{
        private  int idEncuesta;
        private  String matricula;
        private String nombre;
        private long timestamp;
        private  String sintomas;
        private  String resultado;

        public Builder from(final Encuesta encuesta){
            idEncuesta=encuesta.getIdEncuesta();
            matricula=encuesta.getMatricula();
            nombre=encuesta.getNombre();
            timestamp=encuesta.getTimeStamp();
            sintomas=encuesta.getSintomas();
            resultado=encuesta.getResultado();
            return this;
        }

        public Builder idEncuesta(final int idEncuesta){
            this.idEncuesta=idEncuesta;
            return this;
        }

        public Builder matricula(final String matricula){
            this.matricula=matricula;
            return this;
        }

        public Builder nombre(final String nombre){
            this.nombre=nombre;
            return this;
        }

        public Builder timestamp(final long timestamp){
            this.timestamp=timestamp;
            return this;
        }


        public Builder sintomas(final String sintomas){
            this.sintomas=sintomas;
            return this;
        }

        public Builder resultado(final String resultado){
            this.resultado=resultado;
            return this;
        }
        public Encuesta build(){
            return new EncuestaImpl(this);

        }

    }


    public static Builder builder(){
        return new Builder();
    }

    public EncuestaImpl(final Builder builder){

        idEncuesta=builder.idEncuesta;
        matricula=builder.matricula;
        nombre=builder.nombre;
        timestamp=builder.timestamp;
        fecha=getFecha(timestamp);
        sintomas=builder.sintomas;
        resultado=builder.resultado;
    }

    private String getFecha(long timestamp) {
        final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy/MM/dd");
        final Instant instant=Instant.ofEpochMilli(timestamp);
        final ZoneId zone = ZoneId.of("America/Mexico_City");
        final ZonedDateTime zonedDateTime=ZonedDateTime.ofInstant(instant,zone);
        return zonedDateTime.format(formatter);
    }

    public String toString(){
        final Gson gson=new Gson();
        return gson.toJson(this);
    }



    @Override
    public int getIdEncuesta() {
        return idEncuesta;
    }

    @Override
    public String getMatricula() {
        return matricula;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public long getTimeStamp() {
        return timestamp;
    }

    @Override
    public String getFecha() {
        return fecha;
    }

    @Override
    public String getSintomas() {
        return sintomas;
    }

    @Override
    public String getResultado() {
        return resultado;
    }
}
