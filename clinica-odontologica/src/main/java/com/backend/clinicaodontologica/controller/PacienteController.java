package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IPacienteService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/registrar")
    public Paciente registrarPaciente(@RequestBody @Valid PacienteEntradaDto paciente){
        return iPacienteService.registrarPaciente(paciente);
    }

    //PUT
    @PutMapping("/actualizar")
    public Paciente actualizarPaciente(@RequestBody Paciente paciente){
        return iPacienteService.actualizarPaciente(paciente);
    }
}
