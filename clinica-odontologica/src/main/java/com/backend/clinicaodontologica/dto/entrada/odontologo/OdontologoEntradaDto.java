package com.backend.clinicaodontologica.dto.entrada.odontologo;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OdontologoEntradaDto {
    @NotNull(message = "El campo numero matricula no puede ser nulo")
    @Digits(integer = 8, fraction = 0, message = "El número matricula debe tener como máximo 8 dígitos")
    //@Pattern(regexp = "\\d{1,8}", message = "El número matricula debe tener como máximo 8 dígitos")
    private int numeroMatricula;
    @NotNull(message = "El nombre del odontólogo no puede ser nulo")
    @NotBlank(message = "Debe especificarse el nombre del odontólogo")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String nombre;
    @NotNull(message = "El apellido del odontólogo no puede ser nulo")
    @NotBlank(message = "Debe especificarse el apellido del odontólogo")
    @Size(max = 50, message = "El apellido debe tener hasta 50 caracteres")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(int numeroMatricula, String nombre, String apellido) {
        this.numeroMatricula = numeroMatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
