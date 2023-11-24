package com.backend.clinicaodontologica.dto.modificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id del turno a modificar")
    private Long id;
    @FutureOrPresent(message = "La fecha y hora del turno a modificar no pueden ser anterior a la del día de hoy")
    @NotNull(message = "Debe especificarse la fecha y hora del turno a modificar")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El id del odontologo del turno a modificar no puede ser nulo")
    private Long odontologoId;
    @NotNull(message = "El id del paciente del turno a modificar no puede ser nulo")
    private Long pacienteId;

    public TurnoModificacionEntradaDto() {
    }
    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, Long odontologoId, Long pacienteId) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
