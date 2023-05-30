package pl.kacperg.workoutsbackend.user.service;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.security.service.TokenService;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserRegisterDTO;
import pl.kacperg.workoutsbackend.user.dto.UserUpdateInfoDTO;
import pl.kacperg.workoutsbackend.user.exception.PasswordSameEmailException;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.model.UserToken;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;
import pl.kacperg.workoutsbackend.utils.email.EmailService;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserTokenRepository userTokenRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Transactional
    public UserDTO register(@Valid UserRegisterDTO registerDTO) throws UserAlreadyExistsException, MessagingException, PasswordSameEmailException {
        validateUserData(registerDTO);
        User newUser = createNewUser(registerDTO);
        log.info("NEW USER CREATED: {}", newUser.getEmail());
        UserToken newUserToken = createUserToken(newUser);
        log.info("USER: {}, TOKEN: {}", newUser.getEmail(), newUserToken.getToken());
        this.emailService.sendInitTokenEmail(registerDTO, newUserToken.getToken());
        return modelMapper.map(newUser, UserDTO.class);
    }

    private void validateUserData(UserRegisterDTO registerDTO) throws UserAlreadyExistsException, PasswordSameEmailException {
        if (this.userRepository.findByEmail(registerDTO.email).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User already exists with email %s", registerDTO.email));
        }
        if (registerDTO.email.equals(registerDTO.password)) {
            throw new PasswordSameEmailException("Password can not be same as email");
        }
    }


    @Transactional
    public UserToken createUserToken(User user) {
        UserToken userToken = UserToken.of()
                .id(0L)
                .user(user)
                .token(tokenService.generateUserToken())
                .build();
        return userTokenRepository.save(userToken);
    }

    @Transactional
    public User createNewUser(UserRegisterDTO registerDTO) {
        return this.userRepository.save(
                new User(null,
                        registerDTO.email,
                        registerDTO.username,
                        this.encoder.encode(registerDTO.password),
                        Scope.USER,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusYears(1),
                        UserStatus.DISABLED));
    }

    public void confirmUserToken(String token) {
        this.tokenService.verifyUserToken(token);
    }

    public UserDTO getUserInfoDto(String email) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public void updateUserinfo(String email, UserUpdateInfoDTO userUpdateInfoDTO) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setEmail(userUpdateInfoDTO.email);
        user.setUsername(userUpdateInfoDTO.username);
    }
}
