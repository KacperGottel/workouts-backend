package pl.kacperg.workoutsbackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.service.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@RestController("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_USER)')")
    @GetMapping("")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) throws UserPrincipalNotFoundException {
        UserDTO userDTO = this.userService.getUserInfoDto(principal.getName());
        return ResponseEntity.ok(userDTO);
    }
}
