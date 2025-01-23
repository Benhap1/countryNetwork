package ru.skillbox.country.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtParser jwtParser;
    private final UserDetailsService userDetailsService;

    public void authenticateBy(String token) {
        String email = jwtParser.getEmailFromToken(token);
        log.info("Почта для аутентификации: {}", email);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("Аутентификация пользователя c почтой: {} успешна", email);
    }
}
