package ar.edu.utn.frc.tup.lciii.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    private Long id;
    private Deck deck;
    private Card lastCard;
    private Integer nextCardIndex;
    private Player playerOne;
    private Player playerTwo;
    private Player nextToPlay;
    private Player winner;
    private MatchStatus matchStatus;
}
