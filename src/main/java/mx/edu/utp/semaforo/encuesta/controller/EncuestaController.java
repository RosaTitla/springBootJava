package mx.edu.utp.semaforo.encuesta.controller;

import mx.edu.utp.semaforo.api.JSend;
import mx.edu.utp.semaforo.api.Success;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.encuesta.domain.EncuestaMapper;
import mx.edu.utp.semaforo.utils.Mapper;
import org.springframework.http.HttpEntity;
import mx.edu.utp.semaforo.encuesta.domain.Encuesta;
import mx.edu.utp.semaforo.encuesta.domain.EncuestaImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;


@RestController
@CrossOrigin(origins = "*")
public class EncuestaController {
    private static final Logger LOGGER = getLogger(EncuestaController.class);
    @Autowired
    private UseCase<Encuesta,String> createEncuesta;
    @Autowired
    private Mapper<String, Encuesta> encuestaMapper;

    @PostMapping("/api/v1/public/encuesta/new")
    public final JSend<Encuesta> createEncuesta(final HttpEntity<String> entity) {
        //debug(encuesta.toString());
        final String json=entity.getBody();
        final Encuesta encuesta=encuestaMapper.from(json);
        final String result = createEncuesta.execute(encuesta);
        logInfo(result);
        return Success.<Encuesta>builder()
                .message(result)
                .data(encuesta)
                .build();
    }

    private static void debug(final String info) {
        LOGGER.debug(info);
    }

    private static void logInfo(final String info) {
        LOGGER.info(info);
    }

}
