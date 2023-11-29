package com.backend.clinicaodontologica.dto.entrada.odontologo;

import javax.validation.constraints.*;

public class OdontologoEntradaDto {
    @NotNull(message = "El campo numero matricula no puede ser nulo")
    @Digits(integer = 10, fraction = 0, message = "El número matricula debe tener como máximo 10 dígitos")
    @Min( value = 1, message = "El número de marícula debe ser mayor que 0 (cero)")
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
