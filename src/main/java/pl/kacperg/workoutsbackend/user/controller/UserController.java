package pl.kacperg.workoutsbackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserUpdateInfoDTO;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.service.UserService;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final FieldsValidator fieldsValidator;

    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) throws UserNotFoundException {
        UserDTO userDTO = this.userService.getUserInfoDto(principal.getName());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserDTO> updateUserInfo(Principal principal,
                                                  @Validated @RequestBody UserUpdateInfoDTO userUpdateInfoDTO,
                                                  BindingResult bindingResult) throws UserNotFoundException {
        fieldsValidator.validate(bindingResult);
        this.userService.updateUserinfo(principal.getName(), userUpdateInfoDTO);
        return ResponseEntity.ok().build();
    }
}
