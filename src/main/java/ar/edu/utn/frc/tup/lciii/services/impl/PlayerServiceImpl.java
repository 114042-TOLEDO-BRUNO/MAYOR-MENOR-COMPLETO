package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.dtos.player.NewPlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.PlayerResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.UpdatePlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.entities.PlayerEntity;
import ar.edu.utn.frc.tup.lciii.models.Player;
import ar.edu.utn.frc.tup.lciii.repositories.jpa.PlayerJpaRepository;
import ar.edu.utn.frc.tup.lciii.services.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ModelMapper mergerMapper;

    @Override
    public Player getPlayerById(Long id) {
        PlayerEntity playerEntity = playerJpaRepository.getReferenceById(id);
        if(Objects.isNull(playerEntity.getUserName())) {
            throw new EntityNotFoundException();
        }
        return modelMapper.map(playerEntity, Player.class);
    }

    @Override
    public PlayerResponseDTO getPlayerResponseDTOById(Long id) {
        PlayerEntity playerEntity = playerJpaRepository.getReferenceById(id);
        if(Objects.isNull(playerEntity.getUserName())) {
            throw new EntityNotFoundException();
        }
        return modelMapper.map(playerEntity, PlayerResponseDTO.class);
    }

    @Override
    public Player updatePlayerBalance(Player player, BigDecimal newBalance) {
        // Implementar el método de manera tal que guarde en el usuario el nuevo balance pasado por parmetro.
        //  Como resultado del guardado debe retornar el usuario nuevamente con el balance actualizado.
        PlayerEntity playerEntity = modelMapper.map(player, PlayerEntity.class);
        playerEntity.setBalance(newBalance);
        PlayerEntity playerUpdated = playerJpaRepository.saveAndFlush(playerEntity);
        return modelMapper.map(playerUpdated, Player.class);
    }

    @Override
    public PlayerResponseDTO getPlayerByUserNameAndPassword(String userName, String password) {
        //  Implementar el método de manera tal que retorne el usuario si encuentra una coincidencia
        //  para el userName y la password. Si no no hay coincidencia, debe retornar una excepción del
        //  tipo EntityNotFoundException con el mensaje "Username or password invalid!"
        Optional<PlayerEntity> playerEntityOptional=playerJpaRepository.findByUserNameAndPassword(userName,password);
        if(playerEntityOptional.isPresent()){
            return modelMapper.map(playerEntityOptional.get(),PlayerResponseDTO.class);
        }else{
            throw new EntityNotFoundException("UserName or password invalid!");
        }
    }

    @Override
    public PlayerResponseDTO savePlayer(NewPlayerRequestDTO newPlayerRequestDTO) {
        // TO DO: Implementar el método de manera tal que cree un nuevo usuario con los datos recibidos por parametro
        //  y asigne por defecto un balance de 200 al usuario en la creación
        PlayerEntity playerEntity=modelMapper.map(newPlayerRequestDTO,PlayerEntity.class);
        playerEntity.setBalance(new BigDecimal(200));
        playerJpaRepository.save(playerEntity);
        PlayerResponseDTO playerDTO=modelMapper.map(playerEntity,PlayerResponseDTO.class);
        return playerDTO;
    }

    @Override
    public PlayerResponseDTO updatePlayer(UpdatePlayerRequestDTO updatePlayerRequestDTO) {
        PlayerEntity playerEntity = playerJpaRepository.getReferenceById(updatePlayerRequestDTO.getId());
        mergerMapper.map(updatePlayerRequestDTO,playerEntity);
        PlayerEntity playerEntitySaved =playerJpaRepository.saveAndFlush(playerEntity);


        return  mergerMapper.map(playerEntitySaved,PlayerResponseDTO.class);

    }

}
