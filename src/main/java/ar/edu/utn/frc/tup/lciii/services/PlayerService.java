package ar.edu.utn.frc.tup.lciii.services;


import ar.edu.utn.frc.tup.lciii.dtos.player.NewPlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.PlayerResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.UpdatePlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.models.Player;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PlayerService {

    PlayerResponseDTO getPlayerResponseDTOById(Long id);

    Player getPlayerById(Long id);

    Player updatePlayerBalance(Player player, BigDecimal newBalance);

    PlayerResponseDTO getPlayerByUserNameAndPassword(String userName, String password);

    PlayerResponseDTO savePlayer(NewPlayerRequestDTO newPlayerRequestDTO);

    PlayerResponseDTO updatePlayer(UpdatePlayerRequestDTO updatePlayerRequestDTO);

}
