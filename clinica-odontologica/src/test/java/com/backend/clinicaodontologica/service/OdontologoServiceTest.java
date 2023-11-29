package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    void deberiaRetornarUnaListaNoVacia() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();

        assertFalse(odontologos.isEmpty());
    }

    @Test
    void deberiaRegistrarUnOdontologo() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(12345679, "Pedro", "Fernandez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto.getId());
        assertEquals(12345679, odontologoSalidaDto.getNumeroMatricula());
        assertEquals("Pedro", odontologoSalidaDto.getNombre());
        assertEquals("Fernandez", odontologoSalidaDto.getApellido());
    }

    @Test
    void deberiaBuscarUnOdontologoPorId() {
        Long odontologoIdPrecargado = 1L;

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(odontologoIdPrecargado);

        assertNotNull(odontologoSalidaDto);
        assertEquals(1, odontologoSalidaDto.getId());
        assertEquals("Juan", odontologoSalidaDto.getNombre());
        assertEquals("Perez", odontologoSalidaDto.getApellido());
        assertEquals(12345678, odontologoSalidaDto.getNumeroMatricula());
    }

    @Test
    void deberiaArrojarUnaExcepcionAlELiminarOdontologoSiElIdDelOdontologoNoExiste() {
        Exception exception = assertThrows( ResourceNotFoundException.class, () -> {odontologoService.eliminarOdontologo(10L);});

        String expectedMessage = "No se ha encontrado el odontologo con id 10";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}