package com.backend.parcial.test;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.dao.impl.OdontologoDAOEnMemoria;
import com.backend.parcial.dao.impl.OdontologoDAOH2;
import com.backend.parcial.model.Odontologo;
import com.backend.parcial.service.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    private OdontologoService odontologoService;

    @BeforeAll
    static void doBefore() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'test.sql'", "sa", "sa");

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