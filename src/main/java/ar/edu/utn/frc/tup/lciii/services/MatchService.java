package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.match.MatchResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.match.NewMatchRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.play.PlayRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.play.PlayResponseDTO;
import ar.edu.utn.frc.tup.lciii.models.Match;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {

    List<MatchResponseDTO> getMatchesByPlayer(Long playerId);

    MatchResponseDTO createMatch(NewMatchRequestDTO newMatchRequestDTO);

    Match getMatchById(Long id);

    MatchResponseDTO getMatchResponseDTOById(Long id);

    PlayResponseDTO play(Long matchId, PlayRequestDTO play);
}
