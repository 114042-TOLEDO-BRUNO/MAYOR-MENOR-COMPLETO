package ar.edu.utn.frc.tup.lciii.dtos.play;


import ar.edu.utn.frc.tup.lciii.models.Card;
import ar.edu.utn.frc.tup.lciii.models.MatchStatus;
import ar.edu.utn.frc.tup.lciii.models.PlayDecision;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayResponseDTO {

    @JsonProperty("player")
    private Long player;

    @JsonProperty("decision")
    private PlayDecision decision;

    @JsonProperty("previous_card")
    private Card previousCard;

    @JsonProperty("your_card")
    private Card yourCard;

    @JsonProperty("number_of_cards_in_deck")
    private Integer cardsInDeck;

    @JsonProperty("match_status")
    private MatchStatus matchStatus;
}
