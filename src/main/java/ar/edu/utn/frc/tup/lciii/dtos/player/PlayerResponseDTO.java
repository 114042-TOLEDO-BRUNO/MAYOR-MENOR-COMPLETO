package ar.edu.utn.frc.tup.lciii.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponseDTO {

    private String userName;
    private String avatar;
    private BigDecimal balance;
}
