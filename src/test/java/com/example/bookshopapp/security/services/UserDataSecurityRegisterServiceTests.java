package com.example.bookshopapp.security.services;

import com.example.bookshopapp.data.book.links.User2UserDataSecurity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.security.data.*;
import com.example.bookshopapp.security.repository.User2UserDataSecurityRepository;
import com.example.bookshopapp.security.repository.UserDataSecurityRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDataSecurityRegisterServiceTests {
    private final String email = "test@test.test";
    private final String name = "test";
    private final String phone = "+7-(777)-777-77-77";
    private final String pass = "111111";
    private final String encryptedPass = "$2a$10$TvuKv7Ea/6TUyBJ01.LPx.orKOeKavMcHYSSfYoe0RhMN67.5Q23e";

    private final UserDataSecurityRegisterService userDataSecurityRegisterService;
    private final PasswordEncoder passwordEncoder;
    private UserDataSecurity userDataSecurity;
    private RegistrationForm registrationForm;
    private ContactConfirmationPayload payload;

    @MockBean
    private UserDataSecurityRepository userDataSecurityRepositoryMock;
    @MockBean
    private User2UserDataSecurityRepository user2UserDataSecurityRepository;

    @Autowired
    UserDataSecurityRegisterServiceTests(UserDataSecurityRegisterService userDataSecurityRegisterService,
                                         PasswordEncoder passwordEncoder) {
        this.userDataSecurityRegisterService = userDataSecurityRegisterService;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setName(this.name);
        registrationForm.setEmail(this.email);
        registrationForm.setPass(this.pass);
        registrationForm.setPhone(this.phone);

        payload = new ContactConfirmationPayload();
        payload.setCode(this.pass);
        payload.setContact(this.email);

        userDataSecurity = new UserDataSecurity();
        userDataSecurity.setPhone(this.phone);
        userDataSecurity.setName(this.name);
        userDataSecurity.setEmail(this.email);
        userDataSecurity.setPassword(this.encryptedPass);
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void testRegisterNewUserSuccessful() {
        when(userDataSecurityRepositoryMock.findUserDataSecurityByEmail(email)).thenReturn(null);
        when(userDataSecurityRepositoryMock.findUserDataSecurityByPhone(phone)).thenReturn(null);
        when(user2UserDataSecurityRepository.save(Mockito.any(User2UserDataSecurity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User2UserDataSecurity user2UserDataSecurity = userDataSecurityRegisterService.registerNewUser(registrationForm);
        assertNotNull(user2UserDataSecurity);


        UserDataSecurity userSecurity = user2UserDataSecurity.getUserDataSecurity();
        assertNotNull(userSecurity);

        UserEntity userEntity = user2UserDataSecurity.getUserEntity();
        assertNotNull(userEntity);

        assertTrue(CoreMatchers.is(userSecurity.getName()).matches(registrationForm.getName()));
        assertTrue(CoreMatchers.is(userSecurity.getEmail()).matches(registrationForm.getEmail()));
        assertTrue(CoreMatchers.is(userSecurity.getPhone()).matches(registrationForm.getPhone()));
        assertTrue(passwordEncoder.matches(registrationForm.getPass(), userSecurity.getPassword()));
        assertTrue(CoreMatchers.is(userEntity.getName()).matches(registrationForm.getName()));
    }

    @Test
    void testRegisterNewUserUnsuccessful() {
        when(userDataSecurityRepositoryMock.findUserDataSecurityByEmail(email)).thenReturn(userDataSecurity);
        when(userDataSecurityRepositoryMock.findUserDataSecurityByPhone(phone)).thenReturn(userDataSecurity);
        when(user2UserDataSecurityRepository.save(Mockito.any(User2UserDataSecurity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(IllegalArgumentException.class, () -> {
            userDataSecurityRegisterService.registerNewUser(registrationForm);
        });
    }

    @Test
    void testJwtLogin() {
        when(userDataSecurityRepositoryMock.findUserDataSecurityByEmail(Mockito.any()))
                .thenReturn(userDataSecurity);
        when(userDataSecurityRepositoryMock.findUserDataSecurityByPhone(Mockito.any()))
                .thenReturn(userDataSecurity);
        ContactConfirmationResponse response = userDataSecurityRegisterService.jwtLogin(payload);
        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().isEmpty());

    }

    @Test
    public void testGetCurrentUserWithOAuth2AuthenticationToken() {
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        OAuth2AuthenticationToken mockOAuth2AuthenticationToken = Mockito.mock(OAuth2AuthenticationToken.class);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockOAuth2AuthenticationToken);

        OAuth2User mockOAuth2User = Mockito.mock(OAuth2User.class);
        when(mockOAuth2User.getAttribute("name")).thenReturn(name);
        when(mockOAuth2User.getAttribute("email")).thenReturn(email);
        when(mockOAuth2User.getAttribute("phone")).thenReturn(phone);
        when(mockOAuth2User.getAttribute("pass")).thenReturn(pass);

        when(mockOAuth2AuthenticationToken.getPrincipal()).thenReturn(mockOAuth2User);

        UserDataSecurity result = userDataSecurityRegisterService.getCurrentUser();

        assertEquals(phone, result.getPhone());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(pass, result.getPassword());
    }

    @Test
    public void testGetCurrentUserWithUserDataSecurityDetails() {
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        UserDataSecurityDetails userDataSecurityDetails = new UserDataSecurityDetails(
                new UserDataSecurity(name, email, phone, pass));

        when(mockSecurityContext.getAuthentication()).thenReturn(usernamePasswordAuthenticationToken);
        when(usernamePasswordAuthenticationToken.getPrincipal()).thenReturn(userDataSecurityDetails);

        UserDataSecurity result = userDataSecurityRegisterService.getCurrentUser();

        assertEquals(phone, result.getPhone());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(email, result.getEmail());
    }
}