package pl.kacperg.workoutsbackend;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kacperg.workoutsbackend.security.config.RsaKeyProperties;
import pl.kacperg.workoutsbackend.users.model.Role;
import pl.kacperg.workoutsbackend.users.model.User;
import pl.kacperg.workoutsbackend.users.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@RequiredArgsConstructor
public class WorkoutsBackendApplication {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(WorkoutsBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findByEmail("kacper@test.pl").isEmpty()){
                this.userRepository.save(
                        new User(
                                1L,
                                "kacper@test.pl",
                                "Kacper",
                                encoder.encode("W^7HH81xjXi^"),
                                Role.ADMIN.getRoleName()));
            }
        };
    }

}
