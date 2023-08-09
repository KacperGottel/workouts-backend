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
import pl.kacperg.workoutsbackend.exercise.exception.ExerciseNotFoundException;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;

import java.security.Principal;

import static pl.kacperg.workoutsbackend.utils.parser.SortParser.parseSort;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/exercise")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<ExerciseDTO>> getExercisesForAccept(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "category, asc") String sort,
            @RequestParam(defaultValue = "") String filter,
            Principal admin) throws UserNotFoundException, PermissionDeniedException {
        Sort pageableSort = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, pageableSort);
        Page<ExerciseDTO> exerciseDTOS = this.adminService.getExercisesForAcceptance(admin.getName(), pageable, filter);
        return ResponseEntity.ok(exerciseDTOS);
    }

    @PostMapping("/exercise")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> acceptExercise(
            @RequestParam Long id,
            Principal admin) throws UserNotFoundException, PermissionDeniedException, ExerciseNotFoundException {
        this.adminService.acceptExercise(admin.getName(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/exercise")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteExercise(
            @RequestParam Long id,
            Principal admin) throws UserNotFoundException, PermissionDeniedException, ExerciseNotFoundException {
        this.adminService.deleteExercise(admin.getName(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "category, asc") String sort,
            @RequestParam(defaultValue = "") String filter,
            Principal admin) throws UserNotFoundException, PermissionDeniedException {
        Sort pageableSort = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, pageableSort);
        Page<UserDTO> usersDTO = this.adminService.getUsers(admin.getName(), pageable, filter);
        return ResponseEntity.ok(usersDTO);
    }

    @DeleteMapping("/users/block")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> blockUser(
            @RequestParam long id,
            Principal admin) throws UserNotFoundException, PermissionDeniedException  {
        this.adminService.blockUser(admin.getName(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/remove")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> removeUser(
            @RequestParam long id,
            Principal admin) throws UserNotFoundException, PermissionDeniedException {
        this.adminService.removeUser(admin.getName(), id);
        return ResponseEntity.ok().build();
    }
}
