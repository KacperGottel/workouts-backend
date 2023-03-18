package pl.kacperg.workoutsbackend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(builderMethodName = "of")
public class WorkoutDTO {
    @JsonProperty("mobility")
    public ExerciseDTO mobility;
    @JsonProperty("plyo")
    public ExerciseDTO plyo;
    @JsonProperty("push")
    public ExerciseDTO push;
    @JsonProperty("pull")
    public ExerciseDTO pull;
    @JsonProperty("legs_push")
    public ExerciseDTO legsPush;
    @JsonProperty("legs_pull")
    public ExerciseDTO legsPull;
    @JsonProperty("accessory")
    public ExerciseDTO accessory;
    @JsonProperty("abs")
    public ExerciseDTO abs;

}
