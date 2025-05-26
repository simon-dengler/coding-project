package de.keywork.backend.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Provides unit tests for the {@link JwtUtil} class.
 */
public class JwtUtilTest {

    @Autowired
    private final JwtUtil jwtUtil = new JwtUtil("testSecret123#wem497tk3x57txh32f34872xfm3784xh");

    @ParameterizedTest
    @CsvSource({
            "'Simon'",
            "'Max'"
    })
    void jwtUtilTest(String input){
        String token = jwtUtil.generateToken(input);
        String actual = jwtUtil.validateAndExtractUsername(token);
        assertEquals(input, actual);
    }
}
