package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOEnMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoDAOEnMemoria.class);
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
}
