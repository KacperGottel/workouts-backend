package pl.kacperg.workoutsbackend.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty
    public String email;
    @JsonProperty
    public String password;

    public String username;
}
