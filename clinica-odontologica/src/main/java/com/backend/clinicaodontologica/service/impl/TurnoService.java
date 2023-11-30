package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.OdontologoTurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.PacienteTurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.model.Turno;
import com.backend.clinicaodontologica.repository.ITurnoRepository;
import com.backend.clinicaodontologica.service.IOdontologoService;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            turnoSalidaDto = entidadADto(turnoPersistido);

            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
        } else if (pacienteEncontrado == null && odontologoEncontrado != null) {
            throw new BadRequestException("El paciente no existe");
        } else if (pacienteEncontrado != null) {
            throw new BadRequestException("El odontologo no existe");
        } else {
            throw new BadRequestException("No existen ni paciente ni el odontologo");
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
                .map(turno -> entidadADto(turno))
                .toList();

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));
        return  turnosSalidaDto;

    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        boolean turnoEncontrado = turnoRepository.findById(id).isPresent();

        if (turnoEncontrado) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id " + id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con id " + id);
        }

    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        TurnoSalidaDto turnoSalidaDto = null;
        //convertimos mediante el mapper de dto a entidad
        Turno turnoEntidad = modelMapper.map(turno, Turno.class);
        //lamamos a la capa de persistencia
        boolean turnoEncontrado = turnoRepository.findById(turnoEntidad.getId()).isPresent();
        PacienteSalidaDto pacienteEncontrado = pacienteService.buscarPacientePorId(turno.getPacienteId());
        OdontologoSalidaDto odontologoEncontrado = odontologoService.buscarOdontologoPorId(turno.getOdontologoId());

        if (turnoEncontrado && pacienteEncontrado != null && odontologoEncontrado != null) {
            Turno turnoActualizado = turnoRepository.save(turnoEntidad);
            turnoSalidaDto = entidadADto(turnoActualizado);
            LOGGER.warn("Turno actualizado " + JsonPrinter.toString(turno));
        } else if (!turnoEncontrado){
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
            // Lanzar excepción
        }
        return turnoSalidaDto;
    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class);
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class);
    }
    private PacienteTurnoSalidaDto pacienteSalidaDtoASalidaTurnoDto(Long id) {
        return modelMapper.map(pacienteService.buscarPacientePorId(id), PacienteTurnoSalidaDto.class);
    }

    private OdontologoTurnoSalidaDto odontologoSalidaDtoASalidaTurnoDto(Long id) {
        return modelMapper.map(odontologoService.buscarOdontologoPorId(id), OdontologoTurnoSalidaDto.class);
    }

    private TurnoSalidaDto entidadADto(Turno turno) {
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteTurnoSalidaDto(pacienteSalidaDtoASalidaTurnoDto(turno.getPaciente().getId()));
        turnoSalidaDto.setOdontologoTurnoSalidaDto(odontologoSalidaDtoASalidaTurnoDto(turno.getOdontologo().getId()));
        return turnoSalidaDto;
    }
}
