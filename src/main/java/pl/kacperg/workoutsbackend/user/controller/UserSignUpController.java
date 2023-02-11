package pl.kacperg.workoutsbackend.user.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserRegisterDTO;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.service.UserService;

@RestController
@RequestMapping("/api/v1/signup/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserSignUpController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> signUp(@Validated @RequestBody UserRegisterDTO userDTO) throws UserAlreadyExistsException, MessagingException {
        UserDTO dto = this.userService.signUp(userDTO);
        return ResponseEntity.ok(dto);
    }
}
