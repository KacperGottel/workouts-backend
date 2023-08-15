package pl.kacperg.workoutsbackend.exercise.enums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public enum ExerciseCategory {
    @NotEmpty @NotBlank PUSH,
    @NotEmpty @NotBlank PULL,
    @NotEmpty @NotBlank LEGS,
    @NotEmpty @NotBlank ACCESSORY
}
