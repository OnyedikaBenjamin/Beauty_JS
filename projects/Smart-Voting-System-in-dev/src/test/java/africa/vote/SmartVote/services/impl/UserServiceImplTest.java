package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.UpdateUserRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.dtos.responses.LoginData;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.AppUser;
import africa.vote.SmartVote.datas.models.Token;
import africa.vote.SmartVote.datas.repositories.TokenRepository;
import africa.vote.SmartVote.datas.repositories.UserRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.security.config.JWTService;
import africa.vote.SmartVote.services.EmailService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    private UserServiceImpl userServiceMock;
    private UserRepository userRepositoryMock;
    private TokenRepository tokenRepositoryMock;
    private EmailService emailServiceMock;
    private JWTService jwtServiceMock;
    private PasswordEncoder passwordEncoderMock;
    private AuthenticationManager authenticationManagerMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        tokenRepositoryMock = mock(TokenRepository.class);
        emailServiceMock = mock(EmailService.class);
        passwordEncoderMock = mock(PasswordEncoder.class);
        authenticationManagerMock = mock(AuthenticationManager.class);
        jwtServiceMock = mock(JWTService.class);

        userServiceMock = new UserServiceImpl(userRepositoryMock, tokenRepositoryMock, emailServiceMock,
                jwtServiceMock, passwordEncoderMock, authenticationManagerMock);
    }

    @Test
    void saveUser_shouldSaveUser() {
        // Arrange
        AppUser appUser = new AppUser();
        // Act
        userServiceMock.saveUser(appUser);
        // Assert
        verify(userRepositoryMock, times(1)).save(appUser);
    }

    @Test
    void createAccount_shouldCreateAccount() {
        // Arrange
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setEmail("test@test.com");

        AppUser appUser = new AppUser();
        appUser.setId("1");
        appUser.setEmail("test@test.com");
        appUser.setFirstName("Test");
        appUser.setLastName("User");

//        when(userRepositoryMock.verifyUser(any(), any())).thenReturn(1);
        when(userRepositoryMock.findByEmailIgnoreCase(tokenRequest.getEmail())).thenReturn(Optional.of(appUser));

        // Act
        ApiData apiData = userServiceMock.createAccount(tokenRequest);

        // Assert
        assertNotNull(apiData);
        assertEquals("Welcome, Test Account Verified Successfully", apiData.getData());
        verify(userRepositoryMock, times(1)).verifyUser(Status.VERIFIED, tokenRequest.getEmail());
    }

    @Test
    @DisplayName("Test createAccount() method with invalid email")
    public void testCreateAccountWithInvalidEmail() {
        // Given
        TokenRequest tokenRequest = new TokenRequest("johndoe@gmail.com", "token");
        when(userRepositoryMock.findByEmailIgnoreCase(tokenRequest.getEmail())).thenReturn(Optional.empty());

        // When and Then
        assertThrows(GenericException.class, () -> userServiceMock.createAccount(tokenRequest),
                "AppUser Not found");
    }


    @Test
    @DisplayName("Test createAccount() method with valid input")
    public void testCreateAccountWithValidInput() {
        // Given
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setEmail("test@test.com");
        tokenRequest.setToken("token");
        AppUser appUser = new AppUser();
        appUser.setFirstName("Test");
        appUser.setLastName("Testing");
        appUser.setEmail("test@test.com");
        appUser.setCategory(Category.valueOf("COHORT_II"));
        appUser.setPassword("password");

        Token token = new Token();
        token.setToken(tokenRequest.getToken());
        token.setCreatedTime(LocalDateTime.now());
        token.setExpiredTime(LocalDateTime.now().plusMinutes(10));
        token.setAppUser(appUser);

        when(userRepositoryMock.findByEmailIgnoreCase(tokenRequest.getEmail())).thenReturn(Optional.of(appUser));
        when(tokenRepositoryMock.findByToken(tokenRequest.getToken())).thenReturn(Optional.of(token));
        doNothing().when(userRepositoryMock).verifyUser(Status.VERIFIED, tokenRequest.getEmail());

        // When
        ApiData result = userServiceMock.createAccount(tokenRequest);

        // Then
        assertEquals("Welcome, Test Account Verified Successfully", result.getData());
    }

    @Test
    void sendOTP_shouldSendOTP() {
        // Arrange
        ResendTokenRequest tokenRequest = new ResendTokenRequest();
        tokenRequest.setEmail("test@test.com");

        AppUser appUser = new AppUser();
        appUser.setId("1");
        appUser.setEmail("test@test.com");
        appUser.setFirstName("Test");
        appUser.setLastName("User");

        when(userRepositoryMock.findByEmailIgnoreCase(tokenRequest.getEmail())).thenReturn(Optional.of(appUser));

        // Act
        ApiData apiData = userServiceMock.sendOTP(tokenRequest);

        // Assert
        assertNotNull(apiData);
        assertEquals("Token successfully sent to  test@test.com", apiData.getData());
        verify(tokenRepositoryMock, times(1)).findByAppUserId(appUser.getId());
        verify(tokenRepositoryMock, times(1)).save(any(Token.class));
        verify(emailServiceMock, times(1)).sendEmail(eq("test@test.com"), any(String.class));
    }


    @Test
    public void authenticate_ValidUser_ReturnsLoginData() {
        // Arrange
        LoginRequest request = new LoginRequest("test@example.com", "password");
        AppUser user = new AppUser();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setPassword(passwordEncoderMock.encode("password"));

        when(userRepositoryMock.findByEmailIgnoreCase(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(jwtServiceMock.generateToken(user))
                .thenReturn("test_token");

        // Act
        LoginData result = userServiceMock.authenticate(request);

        // Assert
        verify(authenticationManagerMock).authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        assertEquals("test_token", result.getToken());
        assertEquals("Test", result.getFirstName());
    }

    @Test
    public void updateUser_ValidUser_ReturnsUpdatedUser() {
        // Arrange
        AppUser appUser = new AppUser();
        appUser.setFirstName("Test");
        appUser.setLastName("Testing");
        appUser.setEmail("test@test.com");
        appUser.setCategory(Category.valueOf("COHORT_II"));
        appUser.setPassword("password");

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("UpdateTest");
        updateUserRequest.setLastName("UpdateTest");

        when(jwtServiceMock.extractUserName("test_token"))
                .thenReturn("test@test.com");

        when(userRepositoryMock.findByEmailIgnoreCase("test@test.com"))
                .thenReturn(Optional.of(appUser));

        when(userServiceMock.getUserName())
                .thenReturn("test@test.com");
        // Act
        ApiData updatedUser = userServiceMock.updateAppUser(updateUserRequest);

        ApiData apiData = ApiData.builder()
                .data("UpdateTest updated Successfully")
                .build();

        // Assert
        assertEquals(apiData.getData(), updatedUser.getData());
    }
}