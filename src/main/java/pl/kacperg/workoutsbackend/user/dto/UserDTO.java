package pl.kacperg.workoutsbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder(builderMethodName = "of")
public class UserDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("scope")
    private String authority;
    @JsonProperty("status")
    private String status;
    @JsonProperty("user_token")
    private String userToken;
    @JsonProperty("created")
    private String created;
    @JsonProperty("expired")
    private String expired;
}
