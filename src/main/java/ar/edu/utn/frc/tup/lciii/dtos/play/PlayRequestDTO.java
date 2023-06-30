package ar.edu.utn.frc.tup.lciii.dtos.play;

import ar.edu.utn.frc.tup.lciii.models.PlayDecision;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayRequestDTO {

    @NotNull
    @JsonProperty("player")
    private Long player;

    @NotNull
    @JsonProperty("decision")
    private PlayDecision decision;
}
