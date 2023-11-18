package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;

import java.util.List;

public interface IPacienteService {
    PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente);

    PacienteSalidaDto buscarPacientePorId(Long id);

    List<PacienteSalidaDto> listarPacientes();
    void eliminarPaciente(Long id);
    PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto paciente);
}
