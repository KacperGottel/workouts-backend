package pl.kacperg.workoutsbackend.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.dto.WorkoutDTO;
import pl.kacperg.workoutsbackend.exercise.exception.ExcerciseAlreadyExistsException;
import pl.kacperg.workoutsbackend.exercise.service.ExerciseService;

@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER')")
    public ResponseEntity<WorkoutDTO> drawWorkout(){
        return ResponseEntity.ok(this.exerciseService.drawWorkout());
    }
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER')")
    public ResponseEntity<Void> createExercise(@RequestBody ExerciseDTO exerciseDTO) throws ExcerciseAlreadyExistsException {
        this.exerciseService.createExercise(exerciseDTO);
        return ResponseEntity.ok().build();
    }
}
