package pl.kacperg.workoutsbackend.security.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        log.info("User logged: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        return ResponseEntity.ok(tokenDTO);
    }

    @Data
    static
    class TokenDTO {
        private String token;
    }
}
