package pl.kacperg.workoutsbackend.utils.mapper.converter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class ExerciseToExerciseDtoConverter extends AbstractConverter<Exercise, ExerciseDTO>{

    @Override
    protected ExerciseDTO convert(Exercise exercise) {
        return ExerciseDTO.of()
                .id(exercise.getId())
                .category(exercise.getCategory())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .videoUrl(exercise.getVideoUrl())
                .imgUrl(exercise.getImgUrl())
                .build();
    }
}
