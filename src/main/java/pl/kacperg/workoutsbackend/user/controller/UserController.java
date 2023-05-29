package pl.kacperg.workoutsbackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.service.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) throws UserPrincipalNotFoundException {
        UserDTO userDTO = this.userService.getUserInfoDto(principal.getName());
        return ResponseEntity.ok(userDTO);
    }
}
