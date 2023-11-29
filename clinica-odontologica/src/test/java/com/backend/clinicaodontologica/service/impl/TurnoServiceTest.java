package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @BeforeEach
    void doBefore() {
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(1L);
        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(1L);

        if (odontologoSalidaDto == null) {
            OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(12345678, "Juan", "Perez");
            odontologoService.registrarOdontologo(odontologoEntradaDto);
        }

        if (pacienteSalidaDto == null) {
            DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Los Alerces", 2884, "Puerto Montt", "Los Lagos");
            PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Mario", "Fernandez", 1122334455, LocalDate.of(2023, 11, 1), domicilioEntradaDto);

            pacienteService.registrarPaciente(pacienteEntradaDto);
        }
    }

    @Test
    void deberiaRegistrarUnTurno() throws BadRequestException {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), 1L, 1L);

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto.getId());
    }

    @Test
    void deberiaArrojarUnaExcepcionSiElIdDelOdontologoNoExiste() {
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 11, 30, 14,10), 10L, 1L);
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
    void deberiaArrojarUnaExcepcionAlEliminarTurnoSiElIdDelTurnoNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {turnoService.eliminarTurno(10L);});

        String expectedMessage = "No se ha encontrado el turno con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
