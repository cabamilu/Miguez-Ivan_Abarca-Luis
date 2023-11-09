package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IPacienteService;
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

    public Paciente registrarPaciente(PacienteEntradaDto paciente) {
        //convertimos mediante el mapper de dto a entidad
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        //lamamos a la capa de persistencia
        return pacienteIDao.registrar(pacienteEntidad);
    }

    public Paciente buscarPacientePorId(int id) {
        return  pacienteIDao.buscarPorId(id);
    }

    public List<Paciente> listarPacientes() { return  pacienteIDao.listarTodos(); }

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
    }
}
