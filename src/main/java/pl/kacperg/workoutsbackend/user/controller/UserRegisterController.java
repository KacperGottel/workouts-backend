package pl.kacperg.workoutsbackend.user.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserRegisterDTO;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.service.UserService;
import pl.kacperg.workoutsbackend.user.exception.PasswordSameEmailException;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

@RestController
@RequestMapping("/api/v1/register/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserRegisterController {

    private final UserService userService;
    private final FieldsValidator fieldsValidator;

    @PostMapping
    public ResponseEntity<UserDTO> register(@Validated @RequestBody UserRegisterDTO userDTO, BindingResult bindingResult)
            throws UserAlreadyExistsException, MessagingException, PasswordSameEmailException {
        fieldsValidator.validate(bindingResult);
        UserDTO dto = this.userService.register(userDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<Void> confirm(@PathVariable String token){
        this.userService.confirmUserToken(token);
        return ResponseEntity.ok().build();
    }
}
