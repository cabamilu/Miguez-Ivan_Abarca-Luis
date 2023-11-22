package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException;

    TurnoSalidaDto buscarTurnoPorId(Long id);

    List<TurnoSalidaDto> listarTurnos();
    void eliminarTurno(Long id);
    TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno);

}
