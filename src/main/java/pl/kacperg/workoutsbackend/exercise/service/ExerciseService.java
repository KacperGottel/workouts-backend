package pl.kacperg.workoutsbackend.exercise.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.dto.WorkoutDTO;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;
import pl.kacperg.workoutsbackend.exercise.exception.ExcerciseAlreadyExistsException;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;
import pl.kacperg.workoutsbackend.exercise.repository.ExerciseRepository;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;

import static pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory.*;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    public WorkoutDTO drawWorkout() {
        return WorkoutDTO.of()
                .mobility(modelMapper.map(findRandomByCategory(MOBILITY), ExerciseDTO.class))
                .plyo(modelMapper.map(findRandomByCategory(PLYO), ExerciseDTO.class))
                .push(modelMapper.map(findRandomByCategory(PUSH), ExerciseDTO.class))
                .pull(modelMapper.map(findRandomByCategory(PULL), ExerciseDTO.class))
                .legsPush(modelMapper.map(findRandomByCategory(LEGS_PUSH), ExerciseDTO.class))
                .legsPull(modelMapper.map(findRandomByCategory(LEGS_PULL), ExerciseDTO.class))
                .accessory(modelMapper.map(findRandomByCategory(ACCESSORY), ExerciseDTO.class))
                .abs(modelMapper.map(findRandomByCategory(ABS), ExerciseDTO.class))
                .build();
    }

    @Transactional
    public void createExercise(@NonNull ExerciseDTO exerciseDTO) throws ExcerciseAlreadyExistsException {
        Exercise exercise = Exercise.of()
                .category(exerciseDTO.getCategory())
                .name(validateName(exerciseDTO.getName()))
                .description(validateDescription(exerciseDTO.getDescription()))
                .videoUrl(exerciseDTO.getVideoUrl())
                .imgUrl(exerciseDTO.getImgUrl())
                .build();
        this.exerciseRepository.save(exercise);
    }

    private String validateDescription(String description) throws ExcerciseAlreadyExistsException {
        boolean isPresent = this.exerciseRepository.findAllByDescriptionContaining(description).isPresent();
        if (isPresent){
            throw new ExcerciseAlreadyExistsException("EXERCISE DESCRIPTION ALREADY EXISTS");
        }
        return description;
    }

    private String validateName(String name) throws ExcerciseAlreadyExistsException {
        boolean isPresent = this.exerciseRepository.findAllByNameContaining(name).isPresent();
        if (isPresent){
            throw new ExcerciseAlreadyExistsException("EXERCISE NAME ALREADY EXISTS");
        }
        return name;
    }

    private Exercise findRandomByCategory(ExerciseCategory category) {
        return this.exerciseRepository
                .findRandomByCategory(category.name())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("RANDOM EXERCISE IN %s CATEGORY NOT FOUND", category)));
    }
}
