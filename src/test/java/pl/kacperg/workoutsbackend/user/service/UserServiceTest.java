package pl.kacperg.workoutsbackend.user.service;

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
    void signUp() throws UserAlreadyExistsException {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("email@email.com", "password", "User");
        UserDTO userDTO = this.userService.signUp(userRegisterDTO);
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
        UserToken userToken = UserToken.of()
                .id(1L)
                .user(userEntity)
                .token(tokenService.generateUserToken())
                .build();
        Mockito.when(this.userRepository.findByEmail(userRegisterDTO.email)).thenReturn(Optional.empty());
        Mockito.when(this.userRepository.save(userEntity)).thenReturn(userEntity);
        Mockito.when(this.userService.createNewUser(userRegisterDTO)).thenReturn(userEntity);
        Mockito.when(this.userService.createUserToken(userEntity)).thenReturn(userToken);
        Assertions.assertAll(
                () -> assertNotNull(userDTO.getId()),
                () -> assertEquals(userDTO.getUsername(), userRegisterDTO.username),
                () -> assertEquals(userDTO.getEmail(), userRegisterDTO.email)
        );
    }

    @Test
    void createUserToken() {
    }

    @Test
    void createNewUser() {
    }
}