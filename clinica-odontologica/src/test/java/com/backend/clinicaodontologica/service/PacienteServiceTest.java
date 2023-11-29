package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.impl.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Test
    void deberiaRetornarUnaListaNoVacia() {
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();

        assertFalse(pacientes.isEmpty());
    }

    @Test
    void deberiaRegistrarUnPaciente() {
        DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Las Gaviotas", 1428, "Santiago centro", "Santiago");
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Pedro", "Ramirez", 12345678, LocalDate.of(2023, 11, 30), domicilioEntradaDto);

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Pedro", pacienteSalidaDto.getNombre());
        assertEquals("Ramirez", pacienteSalidaDto.getApellido());
        assertEquals(12345678, pacienteSalidaDto.getDni());
        assertEquals(pacienteSalidaDto.getFechaIngreso(), LocalDate.of(2023, 11, 30));
        assertNotNull(pacienteSalidaDto.getDomicilioSalidaDto().getId());
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getCalle(), "Las Gaviotas");
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getNumero(), 1428);
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getLocalidad(), "Santiago centro");
        assertEquals(pacienteSalidaDto.getDomicilioSalidaDto().getProvincia(), "Santiago");
    }

    @Test
    void deberiaBuscarUnPacientePorId() {
        Long pacienteIdPrecargado = 1L;

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
    void deberiaArrojarUnaExcepcionAlEliminarPacienteSiElIdDelPacienteNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {pacienteService.eliminarPaciente(10L);});

        String expectedMessage = "No se ha encontrado el paciente con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}