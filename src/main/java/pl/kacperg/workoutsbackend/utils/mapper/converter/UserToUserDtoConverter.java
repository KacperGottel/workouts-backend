package pl.kacperg.workoutsbackend.utils.mapper.converter;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class UserToUserDtoConverter extends AbstractConverter<User, UserDTO> {

    private final UserTokenRepository userTokenRepository;

    @Override
    public UserDTO convert(User user) {
        return UserDTO.of()
                .id(user.getId())
                .created(user.getCreated().toString())
                .expired(user.getExpired().toString())
                .authority(user.getScope().name())
                .email(user.getEmail())
                .userToken(userTokenRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new EntityNotFoundException("TOKEN NOT FOUND FOR USER: " + user.getEmail()))
                        .getToken())
                .status(user.getUserStatus().name())
                .username(user.getUsername())
                .build();
    }
}
