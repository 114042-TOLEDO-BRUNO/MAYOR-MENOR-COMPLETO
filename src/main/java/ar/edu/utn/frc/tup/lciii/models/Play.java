package ar.edu.utn.frc.tup.lciii.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Play {

    private Long id;
    private Long matchId;
    private Player playerPlaying;
    private PlayDecision playDecision;
}
