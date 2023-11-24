package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.impl.PacienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Test
    public void deberiaRetornarUnaListaNoVacia() {
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();

        assertFalse(pacientes.isEmpty());
    }

    @Test
    @Transactional
    public void deberiaRegistrarUnPaciente() {
        DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Los Alerces", 1428, "Puerto Montt", "Los Lagos");
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Pedro", "Fernandez", 12345678, LocalDate.of(2023, 11, 30), domicilioEntradaDto);

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertTrue(pacienteSalidaDto.getId() != 0);
    }

    @Test
    public void deberiaBuscarUnPacientePorId() {
        Long pacienteIdPrecargado = Long.valueOf(1);

        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(pacienteIdPrecargado);

        assertNotNull(pacienteSalidaDto);
        assertEquals(1, pacienteSalidaDto.getId());
        assertEquals("Mario", pacienteSalidaDto.getNombre());
        assertEquals("Fernandez", pacienteSalidaDto.getApellido());
        assertEquals(1122334455, pacienteSalidaDto.getDni());
        assertEquals(pacienteSalidaDto.getFechaIngreso(), LocalDate.of(2023, 11, 1));
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getId(), 1);
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getCalle(), "Los Alerces");
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getNumero(), 2884);
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getLocalidad(), "Puerto Montt");
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getProvincia(), "Los Lagos");
    }

    @Test
    @Transactional
    public void deberiaArrojarUnaExcepcionAlEliminarPacienteSiElIdDelPacienteNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {pacienteService.eliminarPaciente(Long.valueOf(10));});

        String expectedMessage = "No se ha encontrado el paciente con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}