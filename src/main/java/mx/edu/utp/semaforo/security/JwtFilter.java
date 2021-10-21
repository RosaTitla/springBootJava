package mx.edu.utp.semaforo.security;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public final class JwtFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger LOGGER = getLogger(JwtFilter.class);
    private final JwtService jwtService;
    private final String authHeader;

    public static AbstractAuthenticationProcessingFilter create(
            final String prefixUrl,
            final JwtService jwtService,
            final String authHeader) {
        return new JwtFilter(prefixUrl, jwtService, authHeader);
    }

    private JwtFilter(
            final String prefixUrl,
            final JwtService jwtService,
            final String authHeader) {
        super(prefixUrl);
        this.jwtService = jwtService;
        this.authHeader = authHeader;
    }

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!requiresAuthentication(httpRequest, httpResponse)) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        final String token = httpRequest.getHeader(authHeader);

        if (null == token) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            final Authentication authentication = jwtService.getAuthorization(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (final JwtException e) {
            LOGGER.error(e.getMessage(), e);
            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        chain.doFilter(httpRequest, response);
    }

    @Override
    public Authentication attemptAuthentication(
            final HttpServletRequest request, final HttpServletResponse response) {
        final String token = request.getHeader(authHeader);

        if (null == token) {
            throw new AuthenticationServiceException("Bad Token");
        }
        return jwtService.getAuthorization(token);
    }
}
