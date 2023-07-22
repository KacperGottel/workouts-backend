package pl.kacperg.workoutsbackend.admin.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kacperg.workoutsbackend.admin.exception.PermissionDeniedException;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.exercise.enums.ExerciseStatus;
import pl.kacperg.workoutsbackend.exercise.exception.ExerciseNotFoundException;
import pl.kacperg.workoutsbackend.exercise.model.Exercise;
import pl.kacperg.workoutsbackend.exercise.repository.ExerciseRepository;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.model.Scope;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public Page<ExerciseDTO> getExercisesForAcceptance(String email, Pageable pageable, String filter) throws UserNotFoundException, PermissionDeniedDataAccessException, PermissionDeniedException {
        validateIsAdmin(email);
        if (filter != null && !filter.isEmpty() && !filter.isBlank()) {
            Page<Exercise> adminExercisesByCriteria = searchByCriteria(pageable, filter);
            return adminExercisesByCriteria.map(exercise -> modelMapper.map(exercise, ExerciseDTO.class));
        }
        Page<Exercise> adminExercises = this.exerciseRepository.findAllByStatus(ExerciseStatus.WAITING_FOR_ACCEPTANCE, pageable);
        return adminExercises.map(exercise -> modelMapper.map(exercise, ExerciseDTO.class));
    }

    @SuppressWarnings("DuplicatedCode")
    private Page<Exercise> searchByCriteria(Pageable pageable, String filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Exercise> query = cb.createQuery(Exercise.class);
        Root<Exercise> root = query.from(Exercise.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter != null && !filter.isEmpty()) {
            Predicate namePredicate = cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%");
            Predicate categoryPredicate = cb.like(cb.lower(root.get("category")), "%" + filter.toLowerCase() + "%");

            predicates.add(cb.or(namePredicate, categoryPredicate));
        }

        predicates.add(cb.equal(root.get("status"), ExerciseStatus.WAITING_FOR_ACCEPTANCE));

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

    public void validateIsAdmin(String email) throws UserNotFoundException, PermissionDeniedException {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (!user.getScope().equals(Scope.ADMIN)) {
            throw new PermissionDeniedException(String.format("%s IS NOT AN ADMIN SCOPE", email));
        }
    }

    @Transactional
    public void acceptExercise(String email, Long id) throws UserNotFoundException, PermissionDeniedException, ExerciseNotFoundException {
        validateIsAdmin(email);
        Exercise exercise = this.exerciseRepository.findByIdAndStatus(id, ExerciseStatus.WAITING_FOR_ACCEPTANCE)
                .orElseThrow(() -> new ExerciseNotFoundException(String.format("Exercise with id %s does not exist", id)));
        exercise.setStatus(ExerciseStatus.APPROVED);
    }

    @Transactional
    public void deleteExercise(String email, Long id) throws UserNotFoundException, PermissionDeniedException, ExerciseNotFoundException {
        validateIsAdmin(email);
        Exercise exercise = this.exerciseRepository.findByIdAndStatus(id, ExerciseStatus.WAITING_FOR_ACCEPTANCE)
                .orElseThrow(() -> new ExerciseNotFoundException(String.format("Exercise with id %s does not exist", id)));
        exercise.setStatus(ExerciseStatus.REJECTED);
    }
}
