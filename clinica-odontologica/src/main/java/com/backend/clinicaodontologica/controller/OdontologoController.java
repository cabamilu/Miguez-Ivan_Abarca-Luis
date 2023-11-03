package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.service.IOdontologoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private IOdontologoService iOdontologoService;

    public OdontologoController(IOdontologoService iOdontologoService) {
        this.iOdontologoService = iOdontologoService;
    }

    @GetMapping("/listar")
    public List<Odontologo> listarOdontologos() {
        return iOdontologoService.listarOdontologos();
    }

    @GetMapping("/buscarPorId")
    public Odontologo buscarOdontologoPorId(@RequestParam int id) {
        return iOdontologoService.buscarOdontologoPorId(id);
    }
}
