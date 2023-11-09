package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.service.IOdontologoService;
import org.springframework.web.bind.annotation.*;

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
}
