package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.model.Paciente;

import java.util.List;

public interface IPacienteService {
    Paciente registrarPaciente(Paciente paciente);

    Paciente buscarPacientePorId(int id);

    List<Paciente> listarPacientes();

}
