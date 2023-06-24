package pl.kacperg.workoutsbackend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;

@Data
@SuperBuilder(builderMethodName = "of")
@RequiredArgsConstructor
public class NewExerciseDTO {

    @JsonProperty("category")
    private ExerciseCategory category;
    @JsonProperty("name")
    @NotBlank
    @NotEmpty
    @NotNull
    private String name;
    @JsonProperty("description")
    @NotBlank
    @NotEmpty
    @NotNull
    private String description;
    @JsonProperty("video_url")
    @NotBlank
    @NotEmpty
    @NotNull
    private String videoUrl;
    @JsonProperty("img_url")
    @NotEmpty
    @NotBlank
    @NotNull
    private String imgUrl;
    @JsonProperty("series")
    @NotEmpty
    @NotBlank
    @NotNull
    private String series;
    @JsonProperty("reps")
    @NotEmpty
    @NotBlank
    @NotNull
    private String reps;
}
