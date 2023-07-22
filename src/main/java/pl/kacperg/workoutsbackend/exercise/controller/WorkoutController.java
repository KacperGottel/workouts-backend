package pl.kacperg.workoutsbackend.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.exercise.dto.WorkoutDTO;
import pl.kacperg.workoutsbackend.exercise.service.ExerciseService;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
@CrossOrigin
public class WorkoutController {

    private final ExerciseService exerciseService;
    private final FieldsValidator fieldsValidator;


    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<WorkoutDTO> drawWorkout(){
        return ResponseEntity.ok(this.exerciseService.drawWorkout());
    }
}
