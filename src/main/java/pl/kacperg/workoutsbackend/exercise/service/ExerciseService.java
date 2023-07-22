package pl.kacperg.workoutsbackend.exercise.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.dto.NewExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.dto.WorkoutDTO;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseStatus;
import pl.kacperg.workoutsbackend.exercise.exception.ExerciseAlreadyExistsException;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;
import pl.kacperg.workoutsbackend.exercise.repository.ExerciseRepository;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;

import static pl.kacperg.workoutsbackend.exercise.enums.ExerciseCategory.*;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public WorkoutDTO drawWorkout() {
        return WorkoutDTO.of()
                .push(modelMapper.map(findRandomByCategory(PUSH), ExerciseDTO.class))
                .pull(modelMapper.map(findRandomByCategory(PULL), ExerciseDTO.class))
                .legs(modelMapper.map(findRandomByCategory(LEGS), ExerciseDTO.class))
                .accessory(modelMapper.map(findRandomByCategory(ACCESSORY), ExerciseDTO.class))
                .build();
    }

    @Transactional
    public void createExercise(@Valid NewExerciseDTO exerciseDTO, String email) throws ExerciseAlreadyExistsException, UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        Exercise exercise = Exercise.of()
                .category(exerciseDTO.getCategory())
                .name(validateName(exerciseDTO.getName()))
                .description(validateDescription(exerciseDTO.getDescription()))
                .videoUrl(exerciseDTO.getVideoUrl())
                .imgUrl(exerciseDTO.getImgUrl())
                .series(Integer.valueOf(exerciseDTO.getSeries()))
                .reps(Integer.valueOf(exerciseDTO.getReps()))
                .user(user)
                .status(ExerciseStatus.WAITING_FOR_ACCEPTANCE)
                .build();
        this.exerciseRepository.save(exercise);
    }

    private String validateDescription(String description) throws ExerciseAlreadyExistsException {
        boolean isPresent = this.exerciseRepository.findAllByDescriptionContaining(description).isPresent();
        if (isPresent) {
            throw new ExerciseAlreadyExistsException("EXERCISE DESCRIPTION ALREADY EXISTS");
        }
        return description;
    }

    private String validateName(String name) throws ExerciseAlreadyExistsException {
        boolean isPresent = this.exerciseRepository.findAllByNameContaining(name).isPresent();
        if (isPresent) {
            throw new ExerciseAlreadyExistsException("EXERCISE NAME ALREADY EXISTS");
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
