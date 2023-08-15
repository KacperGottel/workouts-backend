package pl.kacperg.workoutsbackend.security.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final JwtEncoder encoder;
    private final UserTokenRepository userTokenRepository;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateUserToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void verifyUserToken(String token) {
        this.userTokenRepository
                .findUserTokenByToken(token)
                .ifPresent(userToken -> {
                    userToken.getUser().setUserStatus(UserStatus.ENABLED);
                    userToken.setToken(this.generateUserToken());
                    log.info("User's token confirmed and changed, user: {}", userToken.getUser().getEmail());
                });
    }
}
