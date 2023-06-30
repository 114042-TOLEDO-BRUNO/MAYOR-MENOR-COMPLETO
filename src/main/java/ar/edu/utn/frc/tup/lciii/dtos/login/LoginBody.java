package ar.edu.utn.frc.tup.lciii.dtos.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody {

    @NotNull(message = "username can´t by null")
    @JsonProperty("username")
    private String userName;

    @NotNull(message = "password can´t by null")
    @JsonProperty("password")
    private String password;
}
