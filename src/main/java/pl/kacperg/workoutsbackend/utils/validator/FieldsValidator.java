package pl.kacperg.workoutsbackend.utils.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldsValidator {

    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String errorString = fieldErrors.stream()
                    .map(fieldError -> String.format("[%s]: %s%n", fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.joining());

            throw new IllegalArgumentException("Field validation errors occurred:\n" + errorString);
        }

    }

}
