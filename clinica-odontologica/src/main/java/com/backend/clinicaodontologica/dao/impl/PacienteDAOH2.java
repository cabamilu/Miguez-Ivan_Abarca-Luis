package com.backend.clinicaodontologica.dao.impl;

import com.backend.clinicaodontologica.dao.H2Connection;
import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.model.Domicilio;
import com.backend.clinicaodontologica.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteDAOH2 implements IDao<Paciente> {
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteDAOH2.class);
    private DomicilioDAOH2 domicilioDAOH2;

    @Override
    public Paciente registrar(Paciente paciente) {
        Connection connection = null;
        Paciente pacientePersistido = null;
        Domicilio domicilioPersistido = null;
        int domicilioId = paciente.getDomicilio().getId();

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            if (domicilioId == 0) {
                domicilioDAOH2 = new DomicilioDAOH2();
                domicilioPersistido = domicilioDAOH2.registrar(paciente.getDomicilio());
                domicilioId = domicilioPersistido.getId();
            }

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PACIENTES (APELLIDO, NOMBRE, DNI, FECHA_INGRESO, DOMICILIO_ID) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, paciente.getApellido());
            preparedStatement.setString(2, paciente.getNombre());
            preparedStatement.setInt(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setInt(5, domicilioId);
            preparedStatement.execute();

            connection.commit();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                pacientePersistido = new Paciente(resultSet.getInt(1), paciente.getApellido(), paciente.getNombre(), paciente.getDni(), paciente.getFechaIngreso(), domicilioPersistido);
            }

            LOGGER.info("Se ha registrado el paciente: " + pacientePersistido);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }

        return pacientePersistido;
    }

    @Override
    public Paciente buscarPorId(int id) {
        Connection connection = null;
        Paciente paciente = null;

        try {
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PACIENTES WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                paciente = crearObjetoPaciente(rs);

            }
            LOGGER.info("Se ha encontrado el paciente " + paciente);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return paciente;
    }

    @Override
    public List<Paciente> listarTodos() {
        Connection connection = null;
        List<Paciente> pacientes = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PACIENTES");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paciente paciente = crearObjetoPaciente(resultSet);
                pacientes.add(paciente);
            }
            LOGGER.info("Listado de todos los pacientes: " + pacientes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return pacientes;
    }

    private Paciente crearObjetoPaciente(ResultSet resultSet) throws SQLException {
        Domicilio domicilio = new DomicilioDAOH2().buscarPorId(resultSet.getInt(6));
        int id = resultSet.getInt(1);
        String apellido = resultSet.getString(2);
        String nombre = resultSet.getString(3);
        int dni = resultSet.getInt(4);
        LocalDate fechaIngreso = resultSet.getDate(5).toLocalDate();

        return new Paciente(id, apellido, nombre, dni, fechaIngreso, domicilio);
    }
}
