package ru.skillbox.country.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.skillbox.country.security.AuthenticationService;
import ru.skillbox.country.security.CheckTokenByOriginal;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidatedTokenFilter extends OncePerRequestFilter {

    private final CheckTokenByOriginal checkTokenByOriginal;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI());
        log.info(request.getContentType());
        if (!request.getRequestURI().contains("storage")) {
            try {
                String token = getToken(request);
                if (token != null) {
                    log.info("Получен токен: {}", token);
                    boolean resultTokenCheckStatus = checkTokenByOriginal.getStatusToken(token);
                    if (resultTokenCheckStatus) {
                        authenticationService.authenticateBy(token);
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization Token");
                    }
                }
            } catch (Exception e) {
                log.error("Невозможно установить аутентификацию пользователя {}", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        log.error("В запросе нет токена");
        return null;
    }
}
