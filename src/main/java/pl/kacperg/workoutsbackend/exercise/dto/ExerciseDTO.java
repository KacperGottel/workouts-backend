package pl.kacperg.workoutsbackend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;

@Data
@SuperBuilder(builderMethodName = "of")
@RequiredArgsConstructor
public class ExerciseDTO {
    @JsonProperty("id")
    @NotBlank
    @NotNull
    private Long id;
    @JsonProperty("category")
    @NotBlank
    @NotNull
    private ExerciseCategory category;
    @JsonProperty("name")
    @NotBlank
    @NotNull
    private String name;
    @JsonProperty("description")
    @NotBlank
    @NotNull
    private String description;
    @JsonProperty("video_url")
    @NotBlank
    @NotNull
    private String videoUrl;
    @JsonProperty("img_url")
    @NotBlank
    @NotNull
    private String imgUrl;
    @JsonProperty("series")
    @NotBlank
    @NotNull
    private String series;
    @JsonProperty("reps")
    @NotBlank
    @NotNull
    private String reps;
    @JsonProperty("status")
    @NotBlank
    @NotNull
    private String status;
}
