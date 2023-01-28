package pl.kacperg.workoutsbackend.user.service;

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

    @Transactional
    public UserDTO signUp(UserRegisterDTO registerDTO) throws UserAlreadyExistsException {
        if (this.userRepository.findByEmail(registerDTO.email).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = createNewUser(registerDTO);
        log.info("NEW USER CREATED: {}", user);
        UserToken userToken = createUserToken(user);
        log.info("NEW USER TOKEN CREATED: {} | FOR USER EMAIL: {}", userToken, user.getEmail());
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserToken createUserToken(User user) {
        UserToken userToken = UserToken.of()
                .id(0L)
                .user(user)
                .token(tokenService.generateUserToken(user))
                .build();
        return userTokenRepository.save(userToken);
    }

    @Transactional
    public User createNewUser(UserRegisterDTO registerDTO) {
        return this.userRepository.save(
                new User(0L,
                        registerDTO.email,
                        registerDTO.username,
                        this.encoder.encode(registerDTO.password),
                        Scope.USER,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusYears(1),
                        UserStatus.DISABLED));
    }
}
