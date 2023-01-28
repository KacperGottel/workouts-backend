package pl.kacperg.workoutsbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {

    @JsonProperty("email")
    @NonNull
    public String email;
    @JsonProperty("password")
    @NonNull
    public String password;
    @JsonProperty("username")
    @NonNull
    public String username;
}
