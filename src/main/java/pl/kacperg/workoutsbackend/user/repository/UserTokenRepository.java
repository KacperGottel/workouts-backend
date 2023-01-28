package pl.kacperg.workoutsbackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kacperg.workoutsbackend.user.model.UserToken;
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {


    UserToken findByUserId(Long id);
}
