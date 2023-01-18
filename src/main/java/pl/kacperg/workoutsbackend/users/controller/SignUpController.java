package pl.kacperg.workoutsbackend.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.users.dto.UserDTO;
import pl.kacperg.workoutsbackend.users.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.users.model.User;
import pl.kacperg.workoutsbackend.users.service.UserService;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@CrossOrigin
public class SignUpController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        User user = this.userService.signUp(userDTO);
        return ResponseEntity.ok(user);
    }
}
