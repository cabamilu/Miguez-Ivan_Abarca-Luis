package com.backend.clinicaodontologica.dao.impl;

import com.backend.clinicaodontologica.dao.IDao;
import com.backend.clinicaodontologica.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOEnMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoDAOEnMemoria.class);
    private List<Odontologo> odontologos = new ArrayList<>();
    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Odontologo odontologoPersistido = new Odontologo(odontologos.size() + 1, odontologo.getNumeroMatricula(), odontologo.getNombre(), odontologo.getApellido());
        odontologos.add(odontologoPersistido);
        LOGGER.info("Se ha registrado un nuevo odontólogo: " + odontologoPersistido);

        return odontologoPersistido;
    }

    @Override
    public List<Odontologo> listarTodos() {
        LOGGER.info("Listado de odontólogos obtenido: " + odontologos);
        return odontologos;
    }

    @Override
    public Odontologo buscarPorId(int id) {
        if (id <= odontologos.size()) return odontologos.get(id);
        return null;
    }
}
