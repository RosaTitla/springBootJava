package mx.edu.utp.semaforo.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.function.BiPredicate;

public class PasswordComparator implements BiPredicate<String,String> {


    private PasswordComparator() {
    }

    public static PasswordComparator create() {
        return new PasswordComparator();
    }

    @Override
    public boolean test(final String password, final String hash) {
        if (null==hash || hash.isBlank()){
            throw new IllegalArgumentException(getEmptyWarning());
        }
        if (null==password || password.isBlank()){
            throw new IllegalArgumentException(getEmptyWarning());
        }
        return BCrypt.checkpw(password,hash);
    }

    private static String getEmptyWarning() {
        return "No se puede comparar un password vacío o nulo";
    }
}
