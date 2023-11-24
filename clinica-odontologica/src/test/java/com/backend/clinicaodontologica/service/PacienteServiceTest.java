package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.service.impl.PacienteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Test
    public void deberiaRetornarUnaListaVacia() {
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();

        assertTrue(pacientes.isEmpty());
    }

    @Test
    @Transactional
    public void deberiaRegistrarUnPaciente() {
        DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Los Alerces", 1428, "Puerto Montt", "Los Lagos");
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Pedro", "Fernandez", 12345678, LocalDate.of(2023, 11, 30), domicilioEntradaDto);

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertTrue(pacienteSalidaDto.getId() != 0);
    }
}