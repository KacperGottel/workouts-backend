package pl.kacperg.workoutsbackend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    public User signUp(UserDTO userDTO) throws UserAlreadyExistsException {
        if(this.userRepository.findByEmail(userDTO.email).isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        return this.userRepository.save(
                    new User(null,
                            userDTO.email,
                            userDTO.username,
                            this.encoder.encode(userDTO.password),
                            Scope.USER,
                            LocalDateTime.now(),
                            LocalDateTime.now().plusYears(1),
                            UserStatus.DISABLED));
    }
}
