package pl.kacperg.workoutsbackend.utils.schedule;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class UserScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "*/30 * * * * *")
    @Transactional
    public void cleanupDeletedUsers() {
        LocalDateTime oneWeekAgo = LocalDate.now().minusWeeks(1).atStartOfDay();
        userRepository.deleteAllByUserStatusAndCreatedBefore(UserStatus.DELETED, oneWeekAgo);
        log.warn("USERS WITH DELETED STATUS DELETED ACCORDING TO SCHEDULE");
    }
}
