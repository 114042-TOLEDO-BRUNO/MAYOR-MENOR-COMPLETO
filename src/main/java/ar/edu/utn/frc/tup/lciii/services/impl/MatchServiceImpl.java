package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.dtos.match.MatchResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.match.NewMatchRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.play.PlayRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.play.PlayResponseDTO;
import ar.edu.utn.frc.tup.lciii.entities.MatchEntity;
import ar.edu.utn.frc.tup.lciii.entities.PlayerEntity;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.repositories.jpa.MatchJpaRepository;
import ar.edu.utn.frc.tup.lciii.services.DeckService;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import ar.edu.utn.frc.tup.lciii.services.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchJpaRepository matchJpaRepository;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private DeckService deckService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<MatchResponseDTO> getMatchesByPlayer(Long playerId) {
        List<MatchResponseDTO> matches = new ArrayList<>();
        // TO DO: Implementar el metodo de manera tal que retorne todas las partidas
        //  en las que haya participado un jugador.
        Optional<List<MatchEntity>>optionalMatchEntities=matchJpaRepository.getAllByPlayerOneOrPlayerTwo(playerId);
        if(optionalMatchEntities.isPresent()){
            optionalMatchEntities.get().forEach(
                    me->{matches.add(modelMapper.map(me,MatchResponseDTO.class));}
            );
            return matches;
        }
        return null;
    }

    @Override
    public MatchResponseDTO createMatch(NewMatchRequestDTO newMatchRequestDTO) {
        Player player1 = playerService.getPlayerById(newMatchRequestDTO.getPlayerOneId());
        Player player2 = playerService.getPlayerById(newMatchRequestDTO.getPlayerTwoId());
        if(player1==null||player2==null){
            throw new EntityNotFoundException("The user {userId} do not exist");
        }
        Match match=new Match();
        match.setPlayerOne(player1);
        match.setPlayerTwo(player2);
        match.setMatchStatus(MatchStatus.PLAYING);
        match.setDeck(deckService.createDeck());
        deckService.shuffleDeck(match.getDeck());
        match.setNextToPlay(player1);
        match.setNextCardIndex(1);
        match.setLastCard(match.getDeck().getCards().get(0));

        MatchEntity matchEntity= matchJpaRepository.saveAndFlush(modelMapper.map(match,MatchEntity.class));
        return modelMapper.map(matchEntity,MatchResponseDTO.class);
        // TO DO: Terminar de implementar el metodo de manera tal que cree un Match nuevo entre dos jugadores.
        //  Si alguno de los jugadores no existe, la partida no puede iniciarse y debe retornarse una excepcion del tipo
        //  EntityNotFoundException con el mensaje "The user {userId} do not exist"
        //  Cuando se cre el Match, debe crearse el mazo (DeckService.createDeck) y mesclarlo (DeckService.shuffleDeck)
        //  El Match siempre arranca con el playerOne iniciando la partida, con el indice 1 nextCardIndex y lastCard
        //  con la primera carta del mazo y con status PLAYING
    }

    @Override
    public Match getMatchById(Long id) {
        MatchEntity me = matchJpaRepository.getReferenceById(id);
        if(me != null) {
            Match match = modelMapper.map(me, Match.class);
            return match;
        }else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public MatchResponseDTO getMatchResponseDTOById(Long id) {
        MatchEntity me = matchJpaRepository.getReferenceById(id);
        if(me != null) {
            return modelMapper.map(me, MatchResponseDTO.class);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Override
    public PlayResponseDTO play(Long matchId, PlayRequestDTO play) {
        PlayResponseDTO playResponseDTO = new PlayResponseDTO();
        Match match = this.getMatchById(matchId);
        if(match.getId()==null){
            throw new EntityNotFoundException("The match "+matchId+" not exist");
        }
        if(play.getPlayer()==null){
            throw new EntityNotFoundException("The user"+play.getPlayer()+" do not exist");
        }
        if(match.getMatchStatus()==MatchStatus.FINISH){
            throw new EntityNotFoundException("Game "+matchId+" is over");
        }
        if(!play.getPlayer().equals(match.getNextToPlay().getId())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404),"It is not the turn of the user"+match.getNextToPlay().getUserName());
        }
        Card cardInGame= deckService.takeCard(match.getDeck(),match.getNextCardIndex());
        Card lastCard=match.getLastCard();
        Integer compareResult= compareCards(cardInGame,lastCard);

        if (play.getDecision() == PlayDecision.MAJOR && compareResult > 0 ||
                play.getDecision() == PlayDecision.MINOR && compareResult < 0 ||
                compareResult == 0){
            match.setLastCard(cardInGame);
            if(match.getPlayerOne().getId().equals(play.getPlayer())){
                match.setNextToPlay(match.getPlayerTwo());
            }else{
                match.setNextToPlay(match.getPlayerOne());
            }
            match.setNextCardIndex(match.getNextCardIndex()+1);
        }else{
            match.setMatchStatus(MatchStatus.FINISH);
            match.setLastCard(cardInGame);
            if(match.getPlayerOne().getId().equals(play.getPlayer())){
                match.setNextToPlay(match.getPlayerTwo());
                match.setWinner(match.getPlayerTwo());
                playerService.updatePlayerBalance(match.getPlayerOne(),match.getPlayerOne().getBalance().subtract(new BigDecimal(10)));
            }else{
                match.setNextToPlay(match.getPlayerOne());
                match.setWinner(match.getPlayerOne());
                playerService.updatePlayerBalance(match.getPlayerTwo(),match.getPlayerTwo().getBalance().subtract(new BigDecimal(10)));
            }
            match.setNextCardIndex(match.getNextCardIndex()+1);
        }
        matchJpaRepository.saveAndFlush(modelMapper.map(match,MatchEntity.class));
        playResponseDTO.setMatchStatus(match.getMatchStatus());
        playResponseDTO.setDecision(play.getDecision());
        playResponseDTO.setYourCard(cardInGame);
        playResponseDTO.setPreviousCard(lastCard);
        playResponseDTO.setPlayer(play.getPlayer());
        playResponseDTO.setCardsInDeck(match.getDeck().getCards().size()-match.getNextCardIndex());


        // TO DO: Terminar de implementar el metodo de manera tal que se ejecute la jugada siguiendo estas reglas:
        //  1 - Si el match no existe disparar una excepcion del tipo EntityNotFoundException
        //      con el mensaje "The match {matchId} do not exist"
        //  2 - Si el jugador no existe disparar una excepcion del tipo EntityNotFoundException
        //      con el mensaje "The user {userId} do not exist"
        //  3 - Si el match ya terminó, disparar una excepcion del tipo MethodArgumentNotValidException
        //      con el mensaje "Game {gameId} is over"
        //  4 - Si el jugador que manda la jugada no es el proximo a jugar, disparar una excepcion del tipo
        //      MethodArgumentNotValidException
        //      con el mensaje "It is not the turn of the user {userName}"
        //  5 - Si está OK, ejecutar la jugada haciendo lo siguiente:
        //      5.1 - Tomar el mazo de la partida y buscar la carta que sigue. Usar el metodo DeckService.takeCard
        //      5.2 - Comparar si la carta tomada del mazo es mayor o menor que la ultima carta que se uso.
        //            Usar el metodo privado compareCards() de esta clase.
        //      5.3 - Comparar si el resultado de la comparacion de las cartas se condice con la decición del jugador
        //      5.4 - Si la respuesta es correcta (coinciden) el juego sigue y se debe actualizar
        //            la ultima carta recogida, el proximo jugador en jugar y el proximo indice de carta a recoger
        //      5.5 - Si la respuesta no incorrecta (no coincide) el juego termina y se debe actualizar
        //            la ultima carta recogida, el proximo jugador en jugar, el proximo indice de carta a recoger,
        //            el ganador
        //            y el estado de la partida
        //      5.6 - Actualizar el Match
        //  6 - Como respuesta, se deben completar los datos de PlayResponseDTO y retornarlo.
        return playResponseDTO;
    }

    private Integer compareCards(Card card1, Card card2) {
        // TO DO: Implementr el metodo de manera tal que retorne:
        //  1 si card1 tiene un valor mayor que card2,
        //  0 si card1 y card2 tienen el mismo valor,
        //  -1 si card1 tiene un valor menor que card 2
        if(card1.getNumber()>card2.getNumber()){
            return 1;
        }else if(card1.getNumber()==card2.getNumber()){
            return 0;
        }else {
            return -1;
        }
    }
}
