package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.impl.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;

    @Test
    void deberiaRetornarUnaListaNoVacia() {
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        assertFalse(turnos.isEmpty());
    }
    @Test
    void deberiaRegistrarUnTurno() throws BadRequestException {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), 2L, 2L);

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto.getId());
    }

    @Test
    void deberiaArrojarUnaExcepcionSiElIdDelOdontologoNoExiste() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), 10L, 2L);
        Exception exception = assertThrows(BadRequestException.class, () -> {turnoService.registrarTurno(turnoEntradaDto);});

        String expectedMessage = "El odontologo no existe";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deberiaArrojarUnaExcepcionSiElIdDelOdontologoYDelPacienteNoExisten() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), 10L, 10L);
        Exception exception = assertThrows(BadRequestException.class, () -> {turnoService.registrarTurno(turnoEntradaDto);});

        String expectedMessage = "No existen ni paciente ni el odontologo";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deberiaArrojarUnaExcepcionSiElIdDelPacienteNoExiste() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), 1L, 10L);
        Exception exception = assertThrows(BadRequestException.class, () -> {turnoService.registrarTurno(turnoEntradaDto);});

        String expectedMessage = "El paciente no existe";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deberiaBuscarUnTurnoPorId() {
        Long turnoIdPrecargado = 1L;

        TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(turnoIdPrecargado);

        assertNotNull(turnoSalidaDto);
        assertEquals(1, turnoSalidaDto.getId());
        assertEquals(1, turnoSalidaDto.getOdontologoSalidaDto().getId());
        assertEquals(1, turnoSalidaDto.getPacienteSalidaDto().getId());
    }

    @Test
    void deberiaArrojarUnaExcepcionAlEliminarTurnoSiElIdDelTurnoNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {turnoService.eliminarTurno(10L);});

        String expectedMessage = "No se ha encontrado el turno con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
