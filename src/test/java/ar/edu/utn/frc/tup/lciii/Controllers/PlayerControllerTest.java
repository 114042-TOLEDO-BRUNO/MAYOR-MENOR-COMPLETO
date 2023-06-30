package ar.edu.utn.frc.tup.lciii.Controllers;

import ar.edu.utn.frc.tup.lciii.controllers.PlayerController;
import ar.edu.utn.frc.tup.lciii.dtos.player.PlayerResponseDTO;
import ar.edu.utn.frc.tup.lciii.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig
@WebMvcTest
public class PlayerControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Mock
    private PlayerService playerService;
    @InjectMocks
    private PlayerController playerController;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void testGetById() throws Exception {
        // Datos de prueba
        Long playerId = 1L;
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        // Configura el comportamiento del servicio mock
        Mockito.when(playerService.getPlayerResponseDTOById(playerId)).thenReturn(playerResponseDTO);
        // Realiza la solicitud GET al endpoint con el ID correspondiente
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/" + playerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Verifica la respuesta
        String responseBody = result.getResponse().getContentAsString();
        // Aquí puedes realizar más afirmaciones en el responseBody según tus necesidades
    }
}