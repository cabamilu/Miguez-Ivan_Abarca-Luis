package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IOdontologoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private IOdontologoService iOdontologoService;

    public OdontologoController(IOdontologoService iOdontologoService) {
        this.iOdontologoService = iOdontologoService;
    }

    @GetMapping
    public List<Odontologo> listarOdontologos() {
        return iOdontologoService.listarOdontologos();
    }

    @GetMapping("/{id}")
    public Odontologo buscarOdontologoPorId(@PathVariable int id) {
        return iOdontologoService.buscarOdontologoPorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarOdontologoPorId(@PathVariable int id) { iOdontologoService.eliminarOdontologo(id); }

    @PostMapping("/registrar")
    public Odontologo registrarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo){
        return iOdontologoService.registrarOdontologo(odontologo);
    }

    @PutMapping("/actualizar")
    public Odontologo actualizarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo){
        return iOdontologoService.actualizarOdontologo(odontologo);
    }

}
