package pl.kacperg.workoutsbackend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(builderMethodName = "of")
@RequiredArgsConstructor
public class WorkoutDTO {
    @JsonProperty("push")
    public ExerciseDTO push;
    @JsonProperty("pull")
    public ExerciseDTO pull;
    @JsonProperty("legs")
    public ExerciseDTO legs;
    @JsonProperty("accessory")
    public ExerciseDTO accessory;

}
