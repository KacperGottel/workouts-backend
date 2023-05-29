package pl.kacperg.workoutsbackend.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.dto.WorkoutDTO;
import pl.kacperg.workoutsbackend.exercise.exception.ExcerciseAlreadyExistsException;
import pl.kacperg.workoutsbackend.exercise.service.ExerciseService;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final FieldsValidator fieldsValidator;


    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<WorkoutDTO> drawWorkout(){
        return ResponseEntity.ok(this.exerciseService.drawWorkout());
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<Void> createExercise(@Validated @RequestBody ExerciseDTO exerciseDTO, BindingResult bindingResult) throws ExcerciseAlreadyExistsException {
        fieldsValidator.validate(bindingResult);
        this.exerciseService.createExercise(exerciseDTO);
        return ResponseEntity.ok().build();
    }
}
