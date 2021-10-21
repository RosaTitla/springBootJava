package mx.edu.utp.semaforo.login.controller;

import mx.edu.utp.semaforo.api.JSend;
import mx.edu.utp.semaforo.api.Success;
import mx.edu.utp.semaforo.api.UseCase;
import mx.edu.utp.semaforo.users.domain.User;
import mx.edu.utp.semaforo.users.domain.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    private static final Logger LOGGER= LoggerFactory.getLogger(LoginController.class);

    private static final String LOGIN="/api/v1/public/login/";

//api/v1/secure/users/new/
    @Autowired
    private UseCase<User,User> authenticateUser;


    @PostMapping(LOGIN)
    public JSend<User> login(@RequestBody final UserImpl user){
        LOGGER.debug(user.toString());
        /*final JwtService jwtService= JwtServiceImpl.create();
        final String token= jwtService.getToken(user);

        final User userWithToken=new UserImpl.Builder()
                .from(user)
                .token(token)
                .build();*/


        final User userWithToken = authenticateUser.execute(user);

        return Success.<User>builder()
                .data(userWithToken)
                .message("El usuario se logueo con exito")
                .build();
    }
}
