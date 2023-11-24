package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.service.impl.OdontologoService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    public void deberiaRetornarUnaListaVacia() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();

        assertTrue(odontologos.isEmpty());
    }

    @Test
    @Transactional
    public void deberiaRegistrarUnOdontologo() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto(12345679, "Mario", "Martinez");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertTrue(odontologoSalidaDto.getId() != 0);
    }
}