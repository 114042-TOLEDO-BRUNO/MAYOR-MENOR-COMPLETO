package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.match.MatchResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.match.NewMatchRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.play.PlayRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.play.PlayResponseDTO;
import ar.edu.utn.frc.tup.lciii.models.Match;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("")
    public ResponseEntity<MatchResponseDTO> saveMatch(@RequestBody @Valid NewMatchRequestDTO newMatchRequestDTO) {
        MatchResponseDTO matchSaved = matchService.createMatch(newMatchRequestDTO);
        if(Objects.isNull(matchSaved)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request has an error");
        } else {
            return ResponseEntity.ok(matchSaved);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchResponseDTOById(id));
    }

    @PostMapping("/{id}/plays/")
    public ResponseEntity<PlayResponseDTO> saveMatch(@PathVariable Long id, @RequestBody @Valid PlayRequestDTO playRequest) {
        return ResponseEntity.ok(matchService.play(id, playRequest));
    }
}
