package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.AppUser;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.RegistrationService;
import africa.vote.SmartVote.services.UserService;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static africa.vote.SmartVote.datas.enums.Category.COHORT_I;
import static africa.vote.SmartVote.datas.enums.Status.UNVERIFIED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegistrationServiceImpl registrationServiceImpl;

    @Captor
    private ArgumentCaptor<AppUser> userCaptor;

    @Captor
    private ArgumentCaptor<ResendTokenRequest> tokenRequestCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testThatUserRegistrationIsSuccessful() {
//        //arrange
//       RegistrationRequest registrationRequest = new RegistrationRequest();
//        registrationRequest.setFirstName("James");
//        registrationRequest.setLastName("Aduloju");
//        registrationRequest.setPhoneNumber("1234567890");
//        registrationRequest.setEmail("adulojujames@gmail.com");
//        registrationRequest.setPassword("password");
//        registrationRequest.setCategory("COHORT_I");
//        registrationRequest.setImageURL("https://james.com/profile-pic.jpg");
//
//        AppUser appUser = AppUser.builder()
//                .firstName(registrationRequest.getFirstName())
//                .lastName(registrationRequest.getLastName())
//                .password("encoded1234")
//                .status(UNVERIFIED)
//                .email(registrationRequest.getEmail().toLowerCase())
//                .imageURL(registrationRequest.getImageURL())
//                .phoneNumber(registrationRequest.getPhoneNumber())
//                .build();
//
//        ResendTokenRequest tokenRequest = ResendTokenRequest.builder()
//                .email(registrationRequest.getEmail())
//                .build();
//
//        ApiData expected = ApiData.builder()
//                .data("AppUser Registration successful")
//                .build();
//
//        when(userService.findByEmailIgnoreCase(registrationRequest.getEmail())).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn("encoded1234");
//
//        //act
//        ApiData result = registrationServiceImpl.register(registrationRequest);
//
//        //assert
//        verify(userService,times(1)).saveUser(any(AppUser.class));
//        verify(userService, times(1)).sendOTP(any(ResendTokenRequest.class));
//        assertEquals(expected, result);
//    }


    @Test
    void testRegisterUser() {

        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("James")
                .lastName("Aduloju")
                .phoneNumber("1234567890")
                .email("james@gmail.com")
                .password("pass1234")
                .category("COHORT_I")
                .imageURL("https://james.com/image.jpg")
                .build();

        when(userService.findByEmailIgnoreCase(registrationRequest.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(registrationRequest.getPassword()))
                .thenReturn("encodedPassword");

        ApiData result = registrationServiceImpl.register(registrationRequest);

        verify(userService, times(1)).findByEmailIgnoreCase(registrationRequest.getEmail());

        verify(userService, times(1)).saveUser(userCaptor.capture());
        AppUser savedUser = userCaptor.getValue();
        assertEquals(registrationRequest.getFirstName(), savedUser.getFirstName());
        assertEquals(registrationRequest.getLastName(), savedUser.getLastName());
        assertEquals(registrationRequest.getPhoneNumber(), savedUser.getPhoneNumber());
        assertEquals(registrationRequest.getEmail().toLowerCase(), savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(Category.getCategory(registrationRequest.getCategory()), savedUser.getCategory());
        assertEquals(registrationRequest.getImageURL(), savedUser.getImageURL());
        assertEquals(Status.UNVERIFIED, savedUser.getStatus());


        verify(userService, times(1)).sendOTP(tokenRequestCaptor.capture());
        ResendTokenRequest sentTokenRequest = tokenRequestCaptor.getValue();
        assertEquals(registrationRequest.getEmail(), sentTokenRequest.getEmail());
        assertEquals("AppUser Registration successful", result.getData());
    }

    @Test
    void testRegisterUserWithEmailAlreadyExists() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("James")
                .lastName("Aduloju")
                .phoneNumber("1234567890")
                .email("james@example.com")
                .password("pass123")
                .category("COHORT_II")
                .imageURL("https://james.com/image.jpg")
                .build();

        when(userService.findByEmailIgnoreCase(registrationRequest.getEmail()))
                .thenReturn(Optional.of(new AppUser()));
        assertThrows(GenericException.class, () -> registrationServiceImpl.register(registrationRequest));
    }

}