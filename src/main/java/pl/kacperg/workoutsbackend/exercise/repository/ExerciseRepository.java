package pl.kacperg.workoutsbackend.exercise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseStatus;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(value = "SELECT * FROM exercise WHERE category = :category ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Exercise> findRandomByCategory(@Param("category") String category);

    Optional<Exercise> findAllByNameContaining(String name);

    Optional<Exercise> findAllByDescriptionContaining(String description);

    Page<Exercise> findAllByUserId(Long id, Pageable pageable);

    Page<Exercise> findAllByStatus(ExerciseStatus exerciseStatus, Pageable pageable);
}
