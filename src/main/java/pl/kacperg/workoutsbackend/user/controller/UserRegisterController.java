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
public class UserRegisterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> register(@Validated @RequestBody UserRegisterDTO userDTO) throws UserAlreadyExistsException, MessagingException {
        UserDTO dto = this.userService.register(userDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<Void> confirm(@PathVariable String token){
        this.userService.confirmUserToken(token);
        return ResponseEntity.ok().build();
    }
}
