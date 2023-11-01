package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.dao.impl.OdontologoDAOEnMemoria;
import com.backend.clinicaodontologica.dao.impl.OdontologoDAOH2;
import com.backend.clinicaodontologica.model.Odontologo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OdontologoServiceTest {
    private OdontologoService odontologoService;

    @BeforeAll
    static void doBefore() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/clinica-odontologica;INIT=RUNSCRIPT FROM 'test.sql'", "sa", "sa");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    void listarOdontologosEnBaseDatosH2() {
        IDao<Odontologo> odontologoDAOH2 = new OdontologoDAOH2();
        odontologoService = new OdontologoService(odontologoDAOH2);

        List<Odontologo> odontologos = odontologoService.listarOdontologos();

        assertEquals( 2, odontologos.size());
    }

    @Test
    void listarOdontologosEnMemoria() {
        IDao<Odontologo> odontologoEnMemoria = new OdontologoDAOEnMemoria();
        odontologoService = new OdontologoService(odontologoEnMemoria);

        List<Odontologo> odontologos = odontologoService.listarOdontologos();

        assertTrue(odontologos.isEmpty());
    }
}