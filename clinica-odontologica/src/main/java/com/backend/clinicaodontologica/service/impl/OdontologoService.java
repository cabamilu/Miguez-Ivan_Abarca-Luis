package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);

    private IDao<Odontologo> odontologoIDao;
    private ModelMapper modelMapper;

    public OdontologoService(IDao<Odontologo> odontologoIDao, ModelMapper modelMapper) {
        this.odontologoIDao = odontologoIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    public  Odontologo registrarOdontologo(OdontologoEntradaDto odontologo) {
        //convertimos mediante el mapper de dto a entidad
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        //lamamos a la capa de persistencia
        return odontologoIDao.registrar(odontologoEntidad);
    }

    @Override
    public Odontologo buscarOdontologoPorId(int id) {
        return odontologoIDao.buscarPorId(id);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoIDao.listarTodos();
    }

    @Override
    public void eliminarOdontologo(int id) {
        odontologoIDao.eliminar(id);
    }

    @Override
    public Odontologo actualizarOdontologo(OdontologoEntradaDto odontologo) {
        //convertimos mediante el mapper de dto a entidad
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        //lamamos a la capa de persistencia
        return odontologoIDao.actualizar(odontologoEntidad);
    }

    private void configureMapping() {
        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class);
    }
}
