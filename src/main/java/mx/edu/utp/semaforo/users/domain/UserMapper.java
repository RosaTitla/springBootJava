package mx.edu.utp.semaforo.users.domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.edu.utp.semaforo.utils.Mapper;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public final class UserMapper implements RowMapper<User> {


    private static final Logger LOGGER = getLogger(UserMapper.class);

    private UserMapper() {
    }

    public static RowMapper<User> create() {
        return new UserMapper();
    }

    @Override
    public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        final String username = rs.getString("username");
        final String firstName = rs.getString("nombre");
        final String fathersLastName = rs.getString("apellidoPaterno");
        final String mothersLastName = rs.getString("apellidoMaterno");
        final String email=rs.getString("email");
        final Long timestamp=rs.getLong("timestamp");
        final int idRole = rs.getInt("idRol");
        final String role = rs.getString("rol");

        return new UserImpl.Builder()
                .username(username)
                .firstName(firstName)
                .fatherLastName(fathersLastName)
                .motherLastName(mothersLastName)
                .email(email)
                .timestamp(timestamp)
                .idRole(idRole)
                .role(role)
                .build();

    }

}
