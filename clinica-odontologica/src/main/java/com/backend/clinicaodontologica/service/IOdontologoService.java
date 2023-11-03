package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.model.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);
    Odontologo buscarOdontologoPorId(int id);
    List<Odontologo> listarOdontologos();

}
