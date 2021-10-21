package mx.edu.utp.semaforo.users.controller;

import mx.edu.utp.semaforo.api.JSend;
import mx.edu.utp.semaforo.api.Success;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.users.domain.UserImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
//@RestController
@CrossOrigin(origins = "*")
public class UsersController {
    private static final Logger LOGGER = getLogger(UsersController.class);
    private static final String USERS = "/api/v1/secure/users/";


    @Autowired
    private UseCase<User, String> createUser;

    @Autowired
    private UseCase<Void, List<User>> getAllUsers;

    @PostMapping(USERS)
    public JSend<User> createUser(@RequestBody final UserImpl user) {
        debug(user.toString());
        final String result = createUser.execute(user);
        logInfo(result);
        return Success.<User>builder()
                .message(result)
                .data(user)
                .build();
    }

    @GetMapping(USERS)
    public JSend<List<User>> getAllUsers() {
        final List<User> users = getAllUsers.execute(null);
        return Success.<List<User>>builder()
                .data(users)
                .build();
    }

    private static void debug(final String info) {
        LOGGER.debug(info);
    }

    private static void logInfo(final String info) {
        LOGGER.info(info);
    }

}
