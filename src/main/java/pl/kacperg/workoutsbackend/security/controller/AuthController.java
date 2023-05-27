package pl.kacperg.workoutsbackend.security.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.security.service.TokenService;

@RestController
@RequestMapping("/api/v1/token")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final TokenService tokenService;

    @PostMapping()
    public ResponseEntity<TokenDTO> token(Authentication authentication) {
        log.info("User logged in: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken() {
        return ResponseEntity.ok(true);
    }

    @Data
    static
    class TokenDTO {
        private String token;
    }
}
