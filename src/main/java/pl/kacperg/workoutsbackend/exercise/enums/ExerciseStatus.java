package pl.kacperg.workoutsbackend.exercise.enums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public enum ExerciseStatus {
    @NotEmpty @NotBlank WAITING_FOR_ACCEPTANCE,
    @NotEmpty @NotBlank APPROVED,
    @NotEmpty @NotBlank REJECTED
}
