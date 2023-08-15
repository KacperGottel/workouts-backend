package pl.kacperg.workoutsbackend.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.exercise.dto.NewExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.exception.ExerciseAlreadyExistsException;
import pl.kacperg.workoutsbackend.exercise.service.ExerciseService;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/exercise")
@RequiredArgsConstructor
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final FieldsValidator fieldsValidator;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<Void> createExercise(
            @Validated @RequestBody NewExerciseDTO exerciseDTO,
            Principal principal,
            BindingResult bindingResult) throws ExerciseAlreadyExistsException, UserNotFoundException {
        fieldsValidator.validate(bindingResult);
        this.exerciseService.createExercise(exerciseDTO, principal.getName());
        return ResponseEntity.ok().build();
    }
}
