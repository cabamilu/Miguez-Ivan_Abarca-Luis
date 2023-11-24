package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    public void deberiaRetornarUnaListaNoVacia() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();

        assertFalse(odontologos.isEmpty());
    }

    @Test
    @Transactional
    public void deberiaRegistrarUnOdontologo() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(12345679, "Pedro", "Fernandez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertTrue(odontologoSalidaDto.getId() != 0);
    }

    @Test
    public void deberiaBuscarUnOdontologoPorId() {
        Long odontologoIdPrecargado = Long.valueOf(1);

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(odontologoIdPrecargado);

        assertNotNull(odontologoSalidaDto);
        assertEquals(1, odontologoSalidaDto.getId());
        assertEquals("Juan", odontologoSalidaDto.getNombre());
        assertEquals("Perez", odontologoSalidaDto.getApellido());
        assertEquals(12345678, odontologoSalidaDto.getNumeroMatricula());
    }

    @Test
    @Transactional
    public void deberiaArrojarUnaExcepcionAlELiminarOdontologoSiElIdDelOdontologoNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {odontologoService.eliminarOdontologo(Long.valueOf(10));});

        String expectedMessage = "No se ha encontrado el odontologo con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}