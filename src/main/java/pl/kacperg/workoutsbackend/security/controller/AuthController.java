package pl.kacperg.workoutsbackend.security.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.security.service.TokenService;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/token")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<TokenDTO> token(Authentication authentication) {
        log.info("User logged in: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setAdmin(authentication.getAuthorities().stream().anyMatch((authority)->authority.getAuthority().equals(Scope.ADMIN.toString())));
        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenValidateDTO> validateToken(Principal principal) throws UserNotFoundException {
        TokenValidateDTO tokenDTO = new TokenValidateDTO();
        tokenDTO.setAdmin(userService.checkIsAdmin(principal.getName()));
        return ResponseEntity.ok(tokenDTO);
    }

    @Data
    static
    class TokenDTO {
        private String token;
        @JsonProperty("is_admin")
        private boolean isAdmin;
    }
    @Data
    static
    class TokenValidateDTO {
        @JsonProperty("is_admin")
        private boolean isAdmin;
    }
}
