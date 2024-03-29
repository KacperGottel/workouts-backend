package pl.kacperg.workoutsbackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kacperg.workoutsbackend.exercise.dto.ExerciseDTO;
import pl.kacperg.workoutsbackend.user.dto.UserDTO;
import pl.kacperg.workoutsbackend.user.dto.UserUpdateInfoDTO;
import pl.kacperg.workoutsbackend.user.exception.UserNotFoundException;
import pl.kacperg.workoutsbackend.user.service.UserService;
import pl.kacperg.workoutsbackend.utils.validator.FieldsValidator;

import java.security.Principal;

import static pl.kacperg.workoutsbackend.utils.parser.SortParser.parseSort;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final FieldsValidator fieldsValidator;

    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) throws UserNotFoundException {
        UserDTO userDTO = this.userService.getUserInfoDto(principal.getName());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserDTO> updateUserInfo(Principal principal,
                                                  @Validated @RequestBody UserUpdateInfoDTO userUpdateInfoDTO,
                                                  BindingResult bindingResult) throws UserNotFoundException {
        fieldsValidator.validate(bindingResult);
        this.userService.updateUserinfo(principal.getName(), userUpdateInfoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exercise")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<Page<ExerciseDTO>> getUserExerciseList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "category, asc") String sort,
            @RequestParam(defaultValue = "") String filter,
            Principal principal) throws UserNotFoundException {
        Sort pageableSort = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, pageableSort);
        Page<ExerciseDTO> exerciseDTOS = this.userService.getUserExerciseDtoList(principal.getName(), pageable, filter);
        return ResponseEntity.ok(exerciseDTOS);
    }
}
