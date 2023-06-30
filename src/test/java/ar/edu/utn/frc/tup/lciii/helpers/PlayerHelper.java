package ar.edu.utn.frc.tup.lciii.helpers;

import ar.edu.utn.frc.tup.lciii.dtos.player.NewPlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.entities.PlayerEntity;
import ar.edu.utn.frc.tup.lciii.models.Player;

import java.math.BigDecimal;

public class PlayerHelper {

    public static Player getPlayerOne() {
        return new Player(1L, "hmorais", "password", "email@email.com", "avatar", BigDecimal.valueOf(200L));
    }

    public static NewPlayerRequestDTO getNewPlayerRequestDTOOne() {
        return new NewPlayerRequestDTO("hmorais", "password", "email@email.com");
    }

    public static PlayerEntity getPlayerEntityOne() {
        return new PlayerEntity(1L, "hmorais", "password", "email@email.com", "avatar", BigDecimal.valueOf(200L));
    }
}
