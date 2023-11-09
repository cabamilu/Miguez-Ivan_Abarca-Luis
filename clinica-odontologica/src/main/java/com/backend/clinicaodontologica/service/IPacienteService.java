package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.model.Paciente;

import java.util.List;

public interface IPacienteService {
    Paciente registrarPaciente(PacienteEntradaDto paciente);

    Paciente buscarPacientePorId(int id);

    List<Paciente> listarPacientes();
    void eliminarPaciente(int id);
    Paciente actualizarPaciente(PacienteEntradaDto paciente);
}
