package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lciii.dtos.match.MatchResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.NewPlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.PlayerResponseDTO;
import ar.edu.utn.frc.tup.lciii.dtos.player.UpdatePlayerRequestDTO;
import ar.edu.utn.frc.tup.lciii.models.Match;
import ar.edu.utn.frc.tup.lciii.models.Player;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import ar.edu.utn.frc.tup.lciii.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @Operation(
            summary = "Get a player by id",
            description = "Return a player by they id. If the player doesn't exist return 404")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
                @Content(schema = @Schema(implementation = PlayerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content =
                @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> getById(@PathVariable Long id) {
        PlayerResponseDTO player = playerService.getPlayerResponseDTOById(id);
        return ResponseEntity.ok(player);
    }

    @Operation(
            summary = "Create a new player",
            description = "Return the player created with your id. If a player exist with the username or email, then return 404. " +
                    "Additionally, the email must be valid and the password must have at least 8 characters and contain at least one number, " +
                    "one lowercase letter, one uppercase letter and one special character.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
            @Content(schema = @Schema(implementation = PlayerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Username or email already exists", content =
            @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    @PostMapping("")
    public ResponseEntity<PlayerResponseDTO> savePlayer(@RequestBody @Valid NewPlayerRequestDTO newPlayerRequestDTO) {
        PlayerResponseDTO playerSaved = playerService.savePlayer(newPlayerRequestDTO);
        if(Objects.isNull(playerSaved)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or email already exists");
        } else {
            return ResponseEntity.ok(playerSaved);
        }
    }

    @GetMapping("/{id}/matches")
    public ResponseEntity<List<MatchResponseDTO>> getMatchesOfPlayer(@PathVariable Long id) {
        List<MatchResponseDTO> matches = matchService.getMatchesByPlayer(id);
        return ResponseEntity.ok(matches);
    }
    @PutMapping("")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@RequestBody UpdatePlayerRequestDTO updatePlayerRequestDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(updatePlayerRequestDTO));
    }
}
