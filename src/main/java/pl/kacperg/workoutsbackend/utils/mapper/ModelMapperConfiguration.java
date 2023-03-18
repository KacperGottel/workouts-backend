package pl.kacperg.workoutsbackend.utils.mapper;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kacperg.workoutsbackend.utils.mapper.converter.ExerciseToExerciseDtoConverter;
import pl.kacperg.workoutsbackend.utils.mapper.converter.UserToUserDtoConverter;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfiguration {

    private final UserToUserDtoConverter userToUserDto;
    private final ExerciseToExerciseDtoConverter exerciseToExerciseDto;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(userToUserDto);
        modelMapper.addConverter(exerciseToExerciseDto);
        return modelMapper;
    }
}
