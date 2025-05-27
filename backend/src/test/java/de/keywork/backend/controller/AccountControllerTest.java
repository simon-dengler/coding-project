package de.keywork.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.keywork.backend.dto.UserDto;
import de.keywork.backend.service.UserService;
import de.keywork.backend.util.JwtUtil;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Provides Unit test for the {@link AccountController} Class.
 */
// in order to make testCases() non-static (to call JwtUtil methods)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(AccountController.class)
@Import(JwtUtilTestConfiguration.class)
class AccountControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil ;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * provides test cases
     * @return test cases as stream
     */
    private Stream<Arguments> testCases() {
        String validJwtForSimon = String.join("", "Bearer ", jwtUtil.generateToken("Simon"));
        String validJwtForMax = String.join("", "Bearer ", jwtUtil.generateToken("Max"));
        String nonValidJwt = "Bearer jw9s45z38fgb38z4r";
        var userDtoSimon = new UserDto();
        userDtoSimon.setUsername("Simon");
        return Stream.of(
                // input jwt - expected response code - expected response UserDto
                Arguments.of(validJwtForSimon, HttpStatus.OK, userDtoSimon),
                Arguments.of(nonValidJwt, HttpStatus.UNAUTHORIZED, new UserDto()),
                Arguments.of(validJwtForMax, HttpStatus.UNAUTHORIZED, new UserDto())
        );
    }

    /**
     * Provides test logic for the "/account" endpoint. Parameters are provided as argument stream.
     * @param authorizationHeader
     * @param httpStatus
     * @param expectedUserDto
     * @throws Exception
     */
    @WithMockUser(username = "Simon")
    @ParameterizedTest
    @MethodSource("testCases")
    public void getAccount_returnsUser_whenValidTokenAndLoggedIn(String authorizationHeader, HttpStatus httpStatus, UserDto expectedUserDto) throws Exception{
        // Arrange
        var userDtoSimon = new UserDto();
        userDtoSimon.setUsername("Simon");
        when(userService.loadUserByUsername("Simon", new UserDto())).thenReturn(userDtoSimon);

        // Act
        MockHttpServletResponse response = mockMvc.perform(get("/account")
                        .header("Authorization", authorizationHeader)).andReturn().getResponse();
        UserDto actualUserDto;
        try {
            actualUserDto = objectMapper.readValue(response.getContentAsString(), UserDto.class);
        } catch (Exception e){
            actualUserDto = null;
        }

        // Assert
        assertEquals(httpStatus.value(), response.getStatus());
        if(response.getStatus() == HttpStatus.OK.value()){
            assertEquals(expectedUserDto.getUsername(), actualUserDto.getUsername());
        }
    }
}
