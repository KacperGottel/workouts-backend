package pl.kacperg.workoutsbackend.user.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;
import pl.kacperg.workoutsbackend.exercise.repository.ExerciseRepository;
import pl.kacperg.workoutsbackend.security.service.TokenService;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserRegisterDTO;
import pl.kacperg.workoutsbackend.user.dto.UserUpdateInfoDTO;
import pl.kacperg.workoutsbackend.user.exception.PasswordSameEmailException;
import pl.kacperg.workoutsbackend.user.exception.UserAlreadyExistsException;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;
import pl.kacperg.workoutsbackend.user.model.UserToken;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;
import pl.kacperg.workoutsbackend.user.repository.UserTokenRepository;
import pl.kacperg.workoutsbackend.utils.email.EmailService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final TokenService tokenService;
    private final UserTokenRepository userTokenRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final EntityManager entityManager;

    @Transactional
    public UserDTO register(@Valid UserRegisterDTO registerDTO) throws UserAlreadyExistsException, MessagingException, PasswordSameEmailException {
        validateUserData(registerDTO);
        User newUser = createNewUser(registerDTO);
        log.info("NEW USER CREATED: {}", newUser.getEmail());
        UserToken newUserToken = createUserToken(newUser);
        log.info("USER: {}, TOKEN: {}", newUser.getEmail(), newUserToken.getToken());
        this.emailService.sendInitTokenEmail(registerDTO, newUserToken.getToken());
        return modelMapper.map(newUser, UserDTO.class);
    }

    private void validateUserData(UserRegisterDTO registerDTO) throws UserAlreadyExistsException, PasswordSameEmailException {
        if (this.userRepository.findByEmail(registerDTO.email).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User already exists with email %s", registerDTO.email));
        }
        if (registerDTO.email.equals(registerDTO.password)) {
            throw new PasswordSameEmailException("Password can not be same as email");
        }
    }


    @Transactional
    public UserToken createUserToken(User user) {
        UserToken userToken = UserToken.of()
                .id(0L)
                .user(user)
                .token(tokenService.generateUserToken())
                .build();
        return userTokenRepository.save(userToken);
    }

    @Transactional
    public User createNewUser(UserRegisterDTO registerDTO) {
        return this.userRepository.save(
                new User(null,
                        registerDTO.email,
                        registerDTO.username,
                        this.encoder.encode(registerDTO.password),
                        Scope.USER,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusYears(1),
                        UserStatus.DISABLED));
    }

    public void confirmUserToken(String token) {
        this.tokenService.verifyUserToken(token);
    }

    public UserDTO getUserInfoDto(String email) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public void updateUserinfo(String email, @Valid UserUpdateInfoDTO userUpdateInfoDTO) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setEmail(userUpdateInfoDTO.email);
        user.setUsername(userUpdateInfoDTO.username);
    }

    public Page<ExerciseDTO> getUserExerciseDtoList(String email, Pageable pageable, String filter) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (filter != null && !filter.isEmpty() && !filter.isBlank()) {
            Page<Exercise> userExercisesByCriteria = searchByCriteria(user.getId(), pageable, filter);
            return userExercisesByCriteria.map(exercise -> modelMapper.map(exercise, ExerciseDTO.class));
        }
        Page<Exercise> userExercises = this.exerciseRepository.findAllByUserId(user.getId(), pageable);
        return userExercises.map(exercise -> modelMapper.map(exercise, ExerciseDTO.class));
    }

    private Page<Exercise> searchByCriteria(Long userId, Pageable pageable, String filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Exercise> query = cb.createQuery(Exercise.class);
        Root<Exercise> root = query.from(Exercise.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter != null && !filter.isEmpty()) {
            Predicate namePredicate = cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%");
            Predicate categoryPredicate = cb.like(cb.lower(root.get("category")), "%" + filter.toLowerCase() + "%");

            predicates.add(cb.or(namePredicate, categoryPredicate));
        }

        predicates.add(cb.equal(root.get("user").get("id"), userId));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Exercise> results = entityManager
                .createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        int totalCount = results.size();
        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        int endIndex = Math.min(offset + pageSize, totalCount);
        List<Exercise> pagedResults = results.subList(offset, endIndex);

        return new PageImpl<>(pagedResults, pageable, totalCount);
    }

    public boolean isAdmin(String email) throws UserNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return user.getScope().equals(Scope.ADMIN);
    }
}
