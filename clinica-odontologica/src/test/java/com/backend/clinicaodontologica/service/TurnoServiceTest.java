package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.impl.TurnoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;

    @Test
    public void deberiaRetornarUnaListaNoVacia() {
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        assertFalse(turnos.isEmpty());
    }
    @Test
    @Transactional
    public void deberiaRegistrarUnTurno() throws BadRequestException {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), Long.valueOf(2), Long.valueOf(2));

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertTrue(turnoSalidaDto.getId() != 0);
    }

    @Test
    @Transactional
    public void deberiaArrojarUnaExcepcionSiElIdDelOdontologoNoExiste() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), Long.valueOf(10), Long.valueOf(2));
        Exception exception = assertThrows(BadRequestException.class, () -> {turnoService.registrarTurno(turnoEntradaDto);});

        String expectedMessage = "Debe indicar el id del odontologo";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @Transactional
    public void deberiaArrojarUnaExcepcionSiElIdDelOdontologoYDelPacienteNoExisten() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), Long.valueOf(10), Long.valueOf(10));
        Exception exception = assertThrows(BadRequestException.class, () -> {turnoService.registrarTurno(turnoEntradaDto);});

        String expectedMessage = "Debe indicar el id del paciente y el id del odontologo";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @Transactional
    public void deberiaArrojarUnaExcepcionSiElIdDelPacienteNoExiste() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), Long.valueOf(1), Long.valueOf(10));
        Exception exception = assertThrows(BadRequestException.class, () -> {turnoService.registrarTurno(turnoEntradaDto);});

        String expectedMessage = "Debe indicar el id del paciente";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void deberiaBuscarUnTurnoPorId() {
        Long turnoIdPrecargado = Long.valueOf(1);

        TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(turnoIdPrecargado);

        assertNotNull(turnoSalidaDto);
        assertEquals(1, turnoSalidaDto.getId());
        assertEquals(1, turnoSalidaDto.getOdontologoSalidaDto().getId());
        assertEquals(1, turnoSalidaDto.getPacienteSalidaDto().getId());
    }

    @Test
    @Transactional
    public void deberiaArrojarUnaExcepcionAlEliminarTurnoSiElIdDelTurnoNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {turnoService.eliminarTurno(Long.valueOf(10));});

        String expectedMessage = "No se ha encontrado el turno con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
