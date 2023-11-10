package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IOdontologoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
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

    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {
        LOGGER.info("OdontologoEntradaDto: " + JsonPrinter.toString(odontologo));

        //convertimos mediante el mapper de dto a entidad
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        //lamamos a la capa de persistencia
        Odontologo odontologoPersistido = odontologoIDao.registrar(odontologoEntidad);
        //transformamos la entidad obtenida en salidaDto
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoPersistido, OdontologoSalidaDto.class);

        LOGGER.info("OdontologoSalidaDto: " + JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(int id) {
        Odontologo odontologoEncontrado = odontologoIDao.buscarPorId(id);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if(odontologoEncontrado != null) {
            odontologoSalidaDto = modelMapper.map(odontologoEncontrado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", odontologoSalidaDto);
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return odontologoSalidaDto;
    }

    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologosSalidaDto = odontologoIDao.listarTodos()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los odontologos: {}", odontologosSalidaDto);
        return odontologosSalidaDto;
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
        modelMapper.typeMap(Odontologo.class, OdontologoSalidaDto.class);
    }
}
