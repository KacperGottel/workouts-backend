package pl.kacperg.workoutsbackend.exercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;

@Data
@SuperBuilder(builderMethodName = "of")
public class ExerciseDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("category")
    private ExerciseCategory category;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("video_url")
    private String videoUrl;
    @JsonProperty("img_url")
    private String imgUrl;
}
