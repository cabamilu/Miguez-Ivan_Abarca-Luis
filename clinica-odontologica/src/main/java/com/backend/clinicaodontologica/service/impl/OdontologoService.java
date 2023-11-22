package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.repository.IOdontologoRepository;
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

    private IOdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;

    public OdontologoService(IOdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {
        LOGGER.info("OdontologoEntradaDto: " + JsonPrinter.toString(odontologo));

        //convertimos mediante el mapper de dto a entidad
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        //lamamos a la capa de persistencia
        Odontologo odontologoPersistido = odontologoRepository.save((odontologoEntidad));
        //transformamos la entidad obtenida en salidaDto
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoPersistido, OdontologoSalidaDto.class);

        LOGGER.info("OdontologoSalidaDto: " + JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoEncontrado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if(odontologoEncontrado != null) {
            odontologoSalidaDto = modelMapper.map(odontologoEncontrado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoSalidaDto));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return odontologoSalidaDto;
    }

    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologosSalidaDto = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los odontologos: {}", JsonPrinter.toString(odontologosSalidaDto));
        return odontologosSalidaDto;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        boolean odontologoEncontrado = odontologoRepository.findById(id).isPresent();

        if (odontologoEncontrado) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id " + id);
        } else {
            LOGGER.error("No se ha encontrado el odontologo con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el odontologo con id " + id);
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontologo) {
        //convertimos mediante el mapper de dto a entidad
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        //lamamos a la capa de persistencia
        boolean odontologoEncontrado = odontologoRepository.findById(odontologoEntidad.getId()).isPresent();
        OdontologoSalidaDto odontologoSalidaDto = null;

        if (odontologoEncontrado) {
            odontologoRepository.save(odontologoEntidad);
            odontologoSalidaDto = modelMapper.map(odontologoEntidad, OdontologoSalidaDto.class);
            LOGGER.warn("Odontologo actualizado " + JsonPrinter.toString(odontologo));
        } else {
            LOGGER.error("No fue posible actualizar el odontologo porque no se encuentra en nuestra base de datos");
            // Lanzar excepción
        }
        return odontologoSalidaDto;
    }

    private void configureMapping() {
        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class);
        modelMapper.typeMap(Odontologo.class, OdontologoSalidaDto.class);
        modelMapper.typeMap(OdontologoModificacionEntradaDto.class, Odontologo.class);
    }
}
