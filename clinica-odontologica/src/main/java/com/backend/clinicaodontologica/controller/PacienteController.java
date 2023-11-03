package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IPacienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private IPacienteService iPacienteService;

    public PacienteController(IPacienteService iPacienteService) {
        this.iPacienteService = iPacienteService;
    }

    @GetMapping("/listar")
    public List<Paciente> listarPacientes() {
        return iPacienteService.listarPacientes();
    }
    @GetMapping("/buscarPorId")
    public Paciente buscarOdontologoPorId(@RequestParam int id) {
        return iPacienteService.buscarPacientePorId(id);
    }
}
