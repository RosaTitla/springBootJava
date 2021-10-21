package mx.edu.utp.semaforo.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.Duration;
import java.util.List;

import static java.util.Arrays.asList;

@EnableWebSecurity
@Configurable
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String PROFESOR = "PROFESOR";
    private static final String ESTUDIANTE = "ESTUDIANTE";

    //private static final String AUTH_HEADERS = "x-auth-token";
    private static final String AUTH_HEADERS = "*";
    //private static final String AUTH_HEADERS = "x-auth-token";
    private static final List<String>
            ALLOWED_HEADERS = asList(
            "Accept",
            "Access-Control-Allow-Origin",
            "Access-Control-Request-Headers",
            "Authorization",
            "Cache-Control",
            "Content-Length",
            "Content-Type",
            "Origin",
            "X-Auth-Token",
            "X-Requested-With"
    );


    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/api/v1/public/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers("/api/v1/secure/encuesta/").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/secure/users/").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/api/v1/secure/users/").hasAnyRole(ADMIN, PROFESOR)
                .antMatchers("/api/v1/public/login/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/public/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/secure/encuesta/all").hasAnyRole(ADMIN, PROFESOR, ESTUDIANTE)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        JwtFilter.create(
                                "/api/v1/secure/**",
                                JwtServiceImpl.create(),
                                AUTH_HEADERS),
                        UsernamePasswordAuthenticationFilter.class
                );
        //.permitAll();

    }


    @Bean
    public static CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(asList("http://localhost:4200", "http://PROD")); //TODO: dns publica
       // config.setAllowedHeaders(ALLOWED_HEADERS);
      //  config.setAllowedHeaders("*");
config.addAllowedHeader("*");
        config.setAllowedMethods(asList("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"));
        config.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
