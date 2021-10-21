package mx.edu.utp.semaforo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mx.edu.utp.semaforo.users.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JwtServiceImpl implements JwtService{
    private static final long EXPIRATION_MINUTES = 2L;
    private static final String PLAIN_SECRET = "javaspringboot2021utp";
    private static final ZoneId zone = ZoneId.of("America/Mexico_City");

    private String encodedSecret;

    private JwtServiceImpl() {
    }

    public static JwtService create() {
        return new JwtServiceImpl();
    }

    @Override
    public String getToken(final User user) {
        setEncodedSecret();
        return generateToken(user);
    }

    @Override
    public Authentication getAuthorization(final String token) {
        setEncodedSecret();

        final Claims claims = Jwts.parser()
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .getBody();

        final String username = claims.getSubject();
        final List<String> roles = getRoles(claims);

        final List<? extends GrantedAuthority> authorities = roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    @SuppressWarnings("unchecked")
    private static List<String> getRoles(final Claims claims) {
        return claims.get("role", ArrayList.class);
    }

    private void setEncodedSecret() {
        if (null == encodedSecret || encodedSecret.isBlank()) {
            encodedSecret = generateEncodedSecret();
        }
    }

    private String generateToken(final User user) {
        final String uid = UUID.randomUUID().toString();
        final String username = user.getUsername();
        final List<String> roles = Collections.singletonList(user.getRole());
        final Date issuedAt = getIssueDate();
        final Date expirationDate = getExpirationDate();

        return Jwts.builder()
                .setId(uid)
                .setSubject(username)
                .claim("role", roles)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    private static String generateEncodedSecret() {
        return Base64.getEncoder()
                .encodeToString(PLAIN_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private static Date getIssueDate() {
        final Instant now = Instant.now();
        final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zone);
        return Date.from(zonedDateTime.toInstant());
    }

    private static Date getExpirationDate() {
        final Instant now = Instant.now();
        final ZonedDateTime zonedDateTime =
                ZonedDateTime.ofInstant(now, zone).plusMinutes(EXPIRATION_MINUTES);
        return Date.from(zonedDateTime.toInstant());
    }
}
