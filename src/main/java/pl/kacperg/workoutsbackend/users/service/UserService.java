package pl.kacperg.workoutsbackend.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kacperg.workoutsbackend.users.dto.UserDTO;
import pl.kacperg.workoutsbackend.users.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.users.model.Scope;
import pl.kacperg.workoutsbackend.users.model.User;
import pl.kacperg.workoutsbackend.users.repository.UserRepository;

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
                            Scope.USER));
    }
}
