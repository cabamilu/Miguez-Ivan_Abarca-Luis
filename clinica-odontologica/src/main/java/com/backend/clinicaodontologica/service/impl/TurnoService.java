package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.model.Turno;
import com.backend.clinicaodontologica.repository.ITurnoRepository;
import com.backend.clinicaodontologica.service.IOdontologoService;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private ModelMapper modelMapper;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;

    public TurnoService(ITurnoRepository turnoRepository, ModelMapper modelMapper, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException {
        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));
        TurnoSalidaDto turnoSalidaDto = null;

        PacienteSalidaDto pacienteEncontrado = pacienteService.buscarPacientePorId(turno.getPacienteId());
        OdontologoSalidaDto odontologoEncontrado = odontologoService.buscarOdontologoPorId(turno.getOdontologoId());

        if (pacienteEncontrado != null && odontologoEncontrado != null) {
            //convertimos mediante el mapper de dto a entidad
            Turno turnoEntidad = modelMapper.map(turno, Turno.class);
            //lamamos a la capa de persistencia
            Turno turnoPersistido = turnoRepository.save(turnoEntidad);
            //transformamos la entidad obtenida en salidaDto
            turnoSalidaDto = modelMapper.map(turnoPersistido, TurnoSalidaDto.class);

            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
        } else if (pacienteEncontrado == null && odontologoEncontrado != null) {
            throw new BadRequestException("Debe indicar el id del paciente");
        } else if (pacienteEncontrado != null) {
            throw new BadRequestException("Debe indicar el id del odontologo");
        } else {
            throw new BadRequestException("Debe indicar el id del paciente y el id del odontologo");
        }

        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoEncontrado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoEncontrado != null) {
            turnoSalidaDto = modelMapper.map(turnoEncontrado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoSalidaDto));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return  turnoSalidaDto;

    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnosSalidaDto = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));
        return  turnosSalidaDto;

    }

    @Override
    public void eliminarTurno(Long id) {
        boolean turnoEncontrado = turnoRepository.findById(id).isPresent();

        if (turnoEncontrado) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id " + id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id " + id);
            // Lanzar excepción
        }

    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        //convertimos mediante el mapper de dto a entidad
        Turno turnoEntidad = modelMapper.map(turno, Turno.class);
        //lamamos a la capa de persistencia
        boolean turnoEncontrado = turnoRepository.findById(turnoEntidad.getId()).isPresent();
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoEncontrado) {
            turnoRepository.save(turnoEntidad);
            turnoSalidaDto = modelMapper.map(turnoEntidad, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado " + JsonPrinter.toString(turno));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
            // Lanzar excepción
        }
        return turnoSalidaDto;
    }

    private void configureMapping() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class);
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologo))
                .addMappings(modelMapper -> modelMapper.map(Turno::getPaciente, TurnoSalidaDto::setPaciente));;
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getOdontologo, Turno::setOdontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getPaciente, Turno::setPaciente));;
    }
}
