package pl.kacperg.workoutsbackend.user.service;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kacperg.workoutsbackend.security.service.TokenService;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserRegisterDTO;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.model.UserToken;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserTokenRepository userTokenRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void signUp() throws UserAlreadyExistsException, MessagingException {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("email@email.com", "password", "User");
        User user = User.of()
                .id(1L)
                .username("User")
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusYears(30))
                .password(this.encoder.encode("password"))
                .scope(Scope.USER)
                .email("email@email.com")
                .userStatus(UserStatus.DISABLED)
                .build();
        UserToken userToken = UserToken.of()
                .id(1L)
                .user(user)
                .token("123")
                .build();
        UserDTO userDTO = UserDTO.of()
                .id(user.getId())
                .created(user.getCreated().toString())
                .expired(user.getExpired().toString())
                .authority(user.getScope().name())
                .email(user.getEmail())
                .userToken(String.valueOf(userToken))
                .status(user.getUserStatus().name())
                .username(user.getUsername())
                .build();
        Mockito.when(this.userRepository.findByEmail(userRegisterDTO.email)).thenReturn(Optional.empty());
        Mockito.when(this.userRepository.save(any())).thenReturn(user);
//        Mockito.when(this.userService.createNewUser(any())).thenReturn(user);
//        Mockito.when(this.userService.createUserToken(any())).thenReturn(userToken);
        Mockito.when(this.modelMapper.map(any(), any())).thenReturn(userDTO);
        UserDTO result = this.userService.signUp(userRegisterDTO);
        Assertions.assertAll(
                () -> assertNotNull(result.getId()),
                () -> assertEquals(result.getUsername(), userRegisterDTO.username),
                () -> assertEquals(result.getEmail(), userRegisterDTO.email)
        );
    }

    @Test
    void createUserToken() {
        User userEntity = User.of()
                .id(1L)
                .username("User")
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusYears(30))
                .password(this.encoder.encode("password"))
                .scope(Scope.USER)
                .email("email@email.com")
                .userStatus(UserStatus.DISABLED)
                .build();

        UserToken token = UserToken.of().token("TOKENTOKENTOKEN").id(0L).user(userEntity).build();

        Mockito.when(userTokenRepository.save(any())).thenReturn(token);
        Mockito.when(tokenService.generateUserToken()).thenReturn(token.getToken());
        UserToken userToken = this.userService.createUserToken(userEntity);
        Assertions.assertAll(
                () -> assertEquals("TOKENTOKENTOKEN", userToken.getToken()),
                () -> assertEquals(userToken.getUser(), userEntity)
        );

    }

    @Test
    void createNewUser() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("email@email.com", "password", "User");
        User userEntity = User.of()
                .id(1L)
                .username("User")
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusYears(30))
                .password(this.encoder.encode("password"))
                .scope(Scope.USER)
                .email("email@email.com")
                .userStatus(UserStatus.DISABLED)
                .build();
        Mockito.when(this.userRepository.save(any())).thenReturn(userEntity);
        User newUser = this.userService.createNewUser(userRegisterDTO);
        Assertions.assertAll(
                () -> assertEquals(userRegisterDTO.email, newUser.getEmail()),
                () -> assertEquals(userRegisterDTO.username, newUser.getUsername())
        );

    }
}