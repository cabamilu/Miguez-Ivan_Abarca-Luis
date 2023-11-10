package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private IDao<Paciente> pacienteIDao;
    private ModelMapper modelMapper;

    public PacienteService(IDao<Paciente> pacienteIDao, ModelMapper modelMapper) {
        this.pacienteIDao = pacienteIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {
        LOGGER.info("PacienteEntradaDto: " + JsonPrinter.toString(paciente));

        //convertimos mediante el mapper de dto a entidad
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        //lamamos a la capa de persistencia
        Paciente pacientePersistido = pacienteIDao.registrar(pacienteEntidad);
        //transformamos la entidad obtenida en salidaDto
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacientePersistido, PacienteSalidaDto.class);

        LOGGER.info("PacienteSalidaDto: " + JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    public PacienteSalidaDto buscarPacientePorId(int id) {
        Paciente pacienteEncontrado = pacienteIDao.buscarPorId(id);
        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteEncontrado != null) {
            pacienteSalidaDto = modelMapper.map(pacienteEncontrado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", pacienteSalidaDto);
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return  pacienteSalidaDto;
    }

    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacientesSalidaDto = pacienteIDao.listarTodos()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los pacientes: {}", pacientesSalidaDto);
        return  pacientesSalidaDto;
    }

    @Override
    public void eliminarPaciente(int id) {
        pacienteIDao.eliminar(id);
    }

    @Override
    public Paciente actualizarPaciente(PacienteEntradaDto paciente) {
        //convertimos mediante el mapper de dto a entidad
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        //lamamos a la capa de persistencia
        return pacienteIDao.actualizar(pacienteEntidad);
    }
    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(modelMapper -> modelMapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
    }
}
