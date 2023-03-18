package pl.kacperg.workoutsbackend.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(value = "SELECT * FROM workouts_db.exercise WHERE category = :category ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Exercise> findRandomByCategory(@Param("category") String category);

    Optional<Exercise> findAllByNameContaining(String name);
    Optional<Exercise> findAllByDescriptionContaining(String description);
}