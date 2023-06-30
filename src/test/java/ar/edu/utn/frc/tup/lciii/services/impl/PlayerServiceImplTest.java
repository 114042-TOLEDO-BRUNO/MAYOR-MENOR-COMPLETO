package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.helpers.PlayerHelper;
import ar.edu.utn.frc.tup.lciii.repositories.jpa.PlayerJpaRepository;
import ar.edu.utn.frc.tup.lciii.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayerServiceImplTest {

    @MockBean
    private PlayerJpaRepository playerJpaRepository;

    @SpyBean
    private PlayerService playerService;

    @Test
    void getPlayerById() {
    }

    @Test
    void getPlayerResponseDTOById() {
    }

    @Test
    void updatePlayerBalance() {
    }

    @Test
    void getPlayerByUserNameAndPassword() {
    }

    @Test
    void savePlayer() {

    }
}