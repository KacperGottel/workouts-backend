package pl.kacperg.workoutsbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateInfoDTO {

    @JsonProperty("email")
    @Email
    @NotBlank
    @NotNull
    public String email;
    @JsonProperty("username")
    @NotBlank
    @NotNull
    @Size(min = 4, max = 20)
    public String username;
}
