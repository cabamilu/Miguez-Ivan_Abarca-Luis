package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.model.Odontologo;
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

    @PostMapping("/registrar")
    public OdontologoSalidaDto registrarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo){
        return iOdontologoService.registrarOdontologo(odontologo);
    }
    @GetMapping("/{id}")
    public OdontologoSalidaDto buscarOdontologoPorId(@PathVariable int id) {
        return iOdontologoService.buscarOdontologoPorId(id);
    }
    @GetMapping
    public List<OdontologoSalidaDto> listarOdontologos() {
        return iOdontologoService.listarOdontologos();
    }
    @PutMapping("/actualizar")
    public Odontologo actualizarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo){
        return iOdontologoService.actualizarOdontologo(odontologo);
    }

}
