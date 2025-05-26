package de.keywork.backend.controller;

import de.keywork.backend.util.JwtUtil;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for the {@link JwtUtil} class.
 */
public class JwtUtilTestConfiguration {
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil("testSecret123#wem497tk3x57txh32f34872xfm3784xh");
    }
}
