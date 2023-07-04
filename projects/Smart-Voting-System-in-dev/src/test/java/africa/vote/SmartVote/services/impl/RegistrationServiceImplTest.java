package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.AppUser;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceImplTest {

    private PasswordEncoder passwordEncoderMock;
    private UserService userServiceMock;
    private RegistrationServiceImpl registrationService;
    private RegistrationRequest registrationRequest;

    @BeforeAll
    public void setUp() {
        passwordEncoderMock = mock(PasswordEncoder.class);
        userServiceMock = mock(UserService.class);
        registrationService = new RegistrationServiceImpl(passwordEncoderMock, userServiceMock);

        registrationRequest = new RegistrationRequest();
    }


    @Test
    public void testRegister_Successful() {
        // given

        registrationRequest.setFirstName("Test");
        registrationRequest.setLastName("Testing");
        registrationRequest.setPhoneNumber("08023325065");
        registrationRequest.setEmail("test@testing.com");
        registrationRequest.setPassword("password");
        registrationRequest.setCategory("COHORT_I");
        registrationRequest.setImageURL("https://example.com/image.jpg");

        when(userServiceMock.findByEmailIgnoreCase(registrationRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoderMock.encode(registrationRequest.getPassword())).thenReturn("encodedPassword");

        // when
        ApiData result = registrationService.register(registrationRequest);

        // then
        verify(userServiceMock).saveUser(any(AppUser.class));
        verify(userServiceMock).sendOTP(any(ResendTokenRequest.class));
        assertEquals("AppUser Registration successful", result.getData());
    }

    @Test
    public void testRegister_UserAlreadyExists() {
        // given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("test@testing.com");

        when(userServiceMock.findByEmailIgnoreCase(registrationRequest.getEmail())).thenReturn(Optional.of(new AppUser()));
        // when
        Executable executable = () -> registrationService.register(registrationRequest);

        // then
        GenericException exception = assertThrows(GenericException.class, executable);
        assertEquals("AppUser with john.doe@example.com already exist in the database", exception.getMessage());
    }

    @Test
    void register_whenSendOTPThrowsException_shouldThrowGenericException() {
        // Arrange
        when(userServiceMock.findByEmailIgnoreCase(registrationRequest.getEmail())).thenReturn(Optional.of(new AppUser()));
        when(userServiceMock.sendOTP(any(ResendTokenRequest.class)))
                .thenThrow(new GenericException("AppUser with test@testing.com already exist in the database"));
    }
}