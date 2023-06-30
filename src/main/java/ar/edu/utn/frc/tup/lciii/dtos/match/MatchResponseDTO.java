package ar.edu.utn.frc.tup.lciii.dtos.match;

import ar.edu.utn.frc.tup.lciii.dtos.player.PlayerResponseDTO;
import ar.edu.utn.frc.tup.lciii.models.Card;
import ar.edu.utn.frc.tup.lciii.models.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponseDTO {

    private Long id;
    private Card lastCard;
    private Integer nextCardIndex;
    private PlayerResponseDTO playerOne;
    private PlayerResponseDTO playerTwo;
    private PlayerResponseDTO nextToPlay;
    private PlayerResponseDTO winner;
    private MatchStatus matchStatus;
}
