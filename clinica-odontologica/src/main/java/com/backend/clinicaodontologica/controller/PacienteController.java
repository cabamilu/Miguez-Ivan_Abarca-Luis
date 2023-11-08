package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IPacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private IPacienteService iPacienteService;

    public PacienteController(IPacienteService iPacienteService) {
        this.iPacienteService = iPacienteService;
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return iPacienteService.listarPacientes();
    }
    @GetMapping("/{id}")
    public Paciente buscarPacientePorId(@PathVariable int id) {
        return iPacienteService.buscarPacientePorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarPacientePorId(@PathVariable int id) { iPacienteService.eliminarPaciente(id); }
}
