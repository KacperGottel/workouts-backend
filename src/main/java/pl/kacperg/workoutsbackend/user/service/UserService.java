package pl.kacperg.workoutsbackend.user.service;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import pl.kacperg.workoutsbackend.utils.email.EmailService;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserTokenRepository userTokenRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Transactional
    public UserDTO signUp(@Valid UserRegisterDTO registerDTO) throws UserAlreadyExistsException, MessagingException {
        if (this.userRepository.findByEmail(registerDTO.email).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = createNewUser(registerDTO);
        log.info("NEW USER CREATED: {}", user);
        UserToken userToken = createUserToken(user);
        log.info("NEW USER TOKEN CREATED: {} | FOR USER EMAIL: {}", userToken, user.getEmail());
        this.emailService.sendInitEmail(registerDTO, userToken.getToken());
        return modelMapper.map(user, UserDTO.class);
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
}
