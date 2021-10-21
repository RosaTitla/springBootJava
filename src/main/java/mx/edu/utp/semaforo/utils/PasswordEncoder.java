package mx.edu.utp.semaforo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.function.UnaryOperator;

public class PasswordEncoder implements UnaryOperator<String>{
    private static final int STRENGTH = 10;
    private static final BCryptPasswordEncoder.BCryptVersion VERSION =
            BCryptPasswordEncoder.BCryptVersion.$2Y;

    private PasswordEncoder() {
    }

    public static UnaryOperator<String> create() {
        return new PasswordEncoder();
    }

    private static String getEncryptedPassword(final String password) {

        if (null == password || password.isBlank()) {
            throw new IllegalArgumentException(getEmptyWarning());
        }

        final BCryptPasswordEncoder encoder;
        encoder = new BCryptPasswordEncoder(VERSION, STRENGTH);
        return encoder.encode(password);
    }

    private static String getEmptyWarning() {
        return "No se puede generar hash de una contrasena vacia";
    }

    @Override
    public String apply(final String password) {
        return getEncryptedPassword(password);
    }
}
