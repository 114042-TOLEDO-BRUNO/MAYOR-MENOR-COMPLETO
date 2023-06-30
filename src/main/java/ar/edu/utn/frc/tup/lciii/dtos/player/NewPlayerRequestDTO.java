package ar.edu.utn.frc.tup.lciii.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPlayerRequestDTO {

    private String userName;
    private String password;
    private String email;

}
