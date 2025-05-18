package de.keywork.backend.util;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
public class JwtUtilTest {
    private final JwtUtil jwtUtil;

    @ParameterizedTest
    @CsvSource({
            "'simon'",
            "'jonasbär123'"
    })
    void jwtUtilTest(String input){
        String token = jwtUtil.generateToken(input);
        String actual = jwtUtil.validateAndExtractUsername(token);
        assertEquals(input, actual);
    }
}
