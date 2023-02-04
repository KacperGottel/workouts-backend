package pl.kacperg.workoutsbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;


@Data
public class UserRegisterDTO {

    @JsonProperty("email")
    @Email
    @NonNull
    @NotBlank
    public String email;
    @JsonProperty("password")
    @NonNull
    @NotBlank
    @Size(min = 4, max = 20)
    public String password;
    @JsonProperty("username")
    @NonNull
    @NotBlank
    @Size(min = 4, max = 20)
    public String username;
}
