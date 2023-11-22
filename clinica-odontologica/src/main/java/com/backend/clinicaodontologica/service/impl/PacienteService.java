package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.repository.IPacienteRepository;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private IPacienteRepository pacienteRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PacienteService(IPacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {
        LOGGER.info("PacienteEntradaDto: " + JsonPrinter.toString(paciente));

        //convertimos mediante el mapper de dto a entidad
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        //lamamos a la capa de persistencia
        Paciente pacientePersistido = pacienteRepository.save(pacienteEntidad);
        //transformamos la entidad obtenida en salidaDto
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacientePersistido, PacienteSalidaDto.class);

        LOGGER.info("PacienteSalidaDto: " + JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    public PacienteSalidaDto buscarPacientePorId(Long id) {
        Paciente pacienteEncontrado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteEncontrado != null) {
            pacienteSalidaDto = modelMapper.map(pacienteEncontrado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteSalidaDto));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return  pacienteSalidaDto;
    }

    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacientesSalidaDto = pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacientesSalidaDto));
        return  pacientesSalidaDto;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        boolean pacienteEncontrado = pacienteRepository.findById(id).isPresent();

        if (pacienteEncontrado) {
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id " + id);
        } else {
            LOGGER.error("No se ha encontrado el paciente con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id " + id);
        }
    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto paciente) {
        //convertimos mediante el mapper de dto a entidad
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        //lamamos a la capa de persistencia
        boolean pacienteEncontrado = pacienteRepository.findById(pacienteEntidad.getId()).isPresent();
        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteEncontrado) {
            pacienteRepository.save(pacienteEntidad);
            pacienteSalidaDto = modelMapper.map(pacienteEntidad, PacienteSalidaDto.class);
            LOGGER.warn("Paciente actualizado " + JsonPrinter.toString(paciente));
        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
            // Lanzar excepción
        }
        return pacienteSalidaDto;
    }
    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(modelMapper -> modelMapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
        modelMapper.typeMap(PacienteModificacionEntradaDto.class, Paciente.class)
                .addMappings(modelMapper -> modelMapper.map(PacienteModificacionEntradaDto::getDomicilioModificacionEntradaDto, Paciente::setDomicilio));
    }
}
