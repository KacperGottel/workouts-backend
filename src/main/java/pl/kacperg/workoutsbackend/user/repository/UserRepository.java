package pl.kacperg.workoutsbackend.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail( String email);

    Optional<User> findByIdAndUserStatusNot(long id, UserStatus status);

    Page<User> findAllByScope(Scope scope, Pageable pageable);

    void deleteAllByUserStatusAndCreatedBefore(UserStatus userStatus, LocalDateTime created);
}
