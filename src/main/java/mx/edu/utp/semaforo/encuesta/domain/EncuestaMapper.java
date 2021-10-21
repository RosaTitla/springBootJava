package mx.edu.utp.semaforo.encuesta.domain;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mx.edu.utp.semaforo.utils.Mapper;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public final class EncuestaMapper implements Mapper<String, Encuesta> {

    private static final Logger LOGGER = getLogger(EncuestaMapper.class);

    private EncuestaMapper() {
    }

    public static Mapper<String, Encuesta> create() {
        return new EncuestaMapper();
    }

    @Override
    public Encuesta from(final String json) {
        if (json.isBlank()){
            throw new IllegalArgumentException(getEmptyMessage());
        }
        LOGGER.debug(json);
        final JsonElement parser= JsonParser.parseString(json);
        final JsonObject jsonObject=parser.getAsJsonObject();

        final String matricula=jsonObject.get("matricula").getAsString();
        final long timestamp=jsonObject.get("timestamp").getAsLong();
        final String sintomas=jsonObject.get("sintomas").toString();
        final String nombre=jsonObject.get("nombre").getAsString();
        LOGGER.info("timestamp"+ timestamp);
        final String resultado=jsonObject.get("resultado").getAsString();


        return  EncuestaImpl.builder()
                .matricula(matricula)
                .nombre(nombre)
                .timestamp(timestamp)
                .sintomas(sintomas)
                .resultado(resultado)
                .build();
    }

    private static String getEmptyMessage() {
        return "El cuerpo de la peticion no puede ser vacio o nulo";
    }
}
