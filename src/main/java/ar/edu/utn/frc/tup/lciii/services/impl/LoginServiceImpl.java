package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.dtos.player.PlayerResponseDTO;
import ar.edu.utn.frc.tup.lciii.entities.PlayerEntity;
import ar.edu.utn.frc.tup.lciii.models.Player;
import ar.edu.utn.frc.tup.lciii.repositories.jpa.PlayerJpaRepository;
import ar.edu.utn.frc.tup.lciii.services.LoginService;
import ar.edu.utn.frc.tup.lciii.services.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
@Autowired
private PlayerJpaRepository playerJpaRepository;
@Autowired
private ModelMapper modelMapper;


    @Override
    public Player login(String userName, String password) {
        // TO DO: Implementar metodo de manera tal que permita a un usuario loguearse en la plataforma
        Optional<PlayerEntity> playerEntity=playerJpaRepository.findByUserNameAndPassword(userName,password);
        if(playerEntity.isPresent()){
            Player player=modelMapper.map(playerEntity,Player.class);
            return player;
        }
            return null;
    }
}
