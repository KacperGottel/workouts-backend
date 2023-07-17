package pl.kacperg.workoutsbackend.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.admin.exception.PermissionDeniedException;
import pl.kacperg.workoutsbackend.admin.service.AdminService;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

import java.security.Principal;

import static pl.kacperg.workoutsbackend.utils.parser.SortParser.parseSort;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;
    private final FieldsValidator fieldsValidator;

    @GetMapping("/exercise")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<ExerciseDTO>> getExercisesForAccept(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "category, asc") String sort,
            @RequestParam(defaultValue = "") String filter,
            Principal principal) throws UserNotFoundException, PermissionDeniedException {
        Sort pageableSort = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, pageableSort);
        Page<ExerciseDTO> exerciseDTOS = this.adminService.getExercisesForAcceptance(principal.getName(), pageable, filter);
        return ResponseEntity.ok(exerciseDTOS);
    }
}
