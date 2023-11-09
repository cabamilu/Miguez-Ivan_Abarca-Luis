package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dao.impl.PacienteDAOH2;
import com.backend.clinicaodontologica.model.Domicilio;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;

public class PacienteServiceTest {

//    private PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
//
//
//    @BeforeAll
//    static void doBefore() {
//        Connection connection = null;
//        try {
//            Class.forName("org.h2.Driver");
//            connection = DriverManager.getConnection("jdbc:h2:~/clinica-odontologica;INIT=RUNSCRIPT FROM 'test.sql'", "sa", "sa");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                connection.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//    @Test
//    void deberiaAgregarUnPaciente() {
//        Domicilio domicilio = new Domicilio("Eleuterio Ramirez", 1331, "Santiago Centro", "Santiago");
//        Paciente paciente = new Paciente("Abarca", "Luis", 12345678, LocalDate.of(2023, 9, 23), domicilio);
//
//        Paciente pacientePersistido = pacienteService.registrarPaciente(paciente);
//
//        assertTrue(pacientePersistido.getId() != 0);
//        assertTrue(pacientePersistido.getDomicilio().getId() != 0);
//    }
//
//    @Test
//    void deberiaRetornarUnaListaNoVacia() {
//        List<Paciente> pacientes = pacienteService.listarPacientes();
//
//        assertFalse(pacientes.isEmpty());
//    }
}