package mx.edu.utp.semaforo.users.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.gson.Gson;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonDeserialize(builder=UserImpl.Builder.class)
public final class UserImpl implements User{

    private final String username;
    private final String password;
    private final String firstName;
    private final String fathersLastName;
    private final String mothersLastName;
    private final String email;
    private final long timestamp;
    private final String fecha;
    private final int idRole;
    private final String role;
    private final String token;


    @SuppressWarnings("PublicConstructor")
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String username;
        private String password;
        private String firstName;
        private String fathersLastName;
        private String mothersLastName;
        private String email;
        private long timestamp;
        private int idRole;
        private String role;
        private String token;

        public Builder from(final User user){
            username = user.getUsername();
            firstName = user.getFirstName();
            fathersLastName = user.getFathersLastName();
            mothersLastName = user.getMothersLastName();
            email=user.getEmail();
            password = user.getPassword();
            timestamp=user.getTimeStamp();
            idRole = user.getIdRole();
            role = user.getRole();
            token = user.getToken();
            return this;
        }


        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder fatherLastName(final String fathersLastName) {
            this.fathersLastName = fathersLastName;
            return this;
        }

        public Builder motherLastName(final String mothersLastName) {
            this.mothersLastName = mothersLastName;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder timestamp(final long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder idRole(final int idRole) {
            this.idRole = idRole;
            return this;
        }

        public Builder role(final String role) {
            this.role = role;
            return this;
        }

        public Builder token(final String token) {
            this.token = token;
            return this;
        }

        public User build() {
            return new UserImpl(this);

        }
    }

    public static Builder builder(){
        return new UserImpl.Builder();
    }



    public UserImpl(final  Builder builder) {
        username = builder.username;
        firstName = builder.firstName;
        fathersLastName = builder.fathersLastName;
        mothersLastName = builder.mothersLastName;
        password = builder.password;
        email=builder.email;
        timestamp=builder.timestamp;
        fecha=getFecha(timestamp);
        idRole = builder.idRole;
        role = builder.role;
        token = builder.token;

    }
    private String getFecha(long timestamp) {
        final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy/MM/dd");
        final Instant instant=Instant.ofEpochMilli(timestamp);
        final ZoneId zone = ZoneId.of("America/Mexico_City");
        final ZonedDateTime zonedDateTime=ZonedDateTime.ofInstant(instant,zone);
        return zonedDateTime.format(formatter);
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getFathersLastName() {
        return fathersLastName;
    }

    @Override
    public String getMothersLastName() {
        return mothersLastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
    public int getIdRole() {
        return idRole;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        final Gson gson= new Gson();
        return gson.toJson(this);

    }
}
