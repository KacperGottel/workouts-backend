package pl.kacperg.workoutsbackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.service.UserService;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@CrossOrigin
public class SignUpController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUp(@Validated @RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        User user = this.userService.signUp(userDTO);
        return ResponseEntity.ok(user);
    }
}
