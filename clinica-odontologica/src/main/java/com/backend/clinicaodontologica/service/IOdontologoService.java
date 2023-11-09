package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.model.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrarOdontologo(OdontologoEntradaDto odontologo);
    Odontologo buscarOdontologoPorId(int id);
    List<Odontologo> listarOdontologos();
    void eliminarOdontologo(int id);
    Odontologo actualizarOdontologo(OdontologoEntradaDto odontologo);
}
