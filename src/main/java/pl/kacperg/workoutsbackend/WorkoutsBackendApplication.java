package pl.kacperg.workoutsbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kacperg.workoutsbackend.security.config.RsaKeyProperties;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.model.UserToken;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@RequiredArgsConstructor
@EnableScheduling
public class WorkoutsBackendApplication {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(WorkoutsBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findByEmail("kacper@test.pl").isEmpty()) {
                User user = this.userRepository.save(
                        new User(
                                1L,
                                "kacper@test.pl",
                                "Kacper",
                                encoder.encode("W^7HH345GhloL0i^"),
                                Scope.ADMIN,
                                LocalDateTime.now(),
                                LocalDateTime.now().plusYears(1),
                                UserStatus.ENABLED));
                UserToken token = UserToken.of().user(user).token(UUID.randomUUID().toString()).build();
                userTokenRepository.save(token);
            }
        };
    }

}
