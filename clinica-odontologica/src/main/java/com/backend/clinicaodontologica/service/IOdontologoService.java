package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    List<OdontologoSalidaDto> listarOdontologos();
    void eliminarOdontologo(Long id);
    OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontologo);
}
