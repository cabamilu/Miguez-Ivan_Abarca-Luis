package com.backend.clinicaodontologica.dto.entrada.turno;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @FutureOrPresent(message = "La fecha y hora no pueden ser anterior a la del día de hoy")
    @NotNull(message = "Debe especificarse la fecha y hora del turno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El odontologo del turno no puede ser nulo")
    @JsonProperty("odontologo")
    @Valid
    private OdontologoTurnoEntradaDto odontologo;
    @NotNull(message = "El paciente del turno no puede ser nulo")
    @JsonProperty("paciente")
    @Valid
    private PacienteTurnoEntradaDto paciente;

    public TurnoEntradaDto() {
    }
    public TurnoEntradaDto(LocalDateTime fechaYHora, OdontologoTurnoEntradaDto odontologo, PacienteTurnoEntradaDto paciente) {
        this.fechaYHora = fechaYHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public OdontologoTurnoEntradaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoTurnoEntradaDto odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteTurnoEntradaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteTurnoEntradaDto paciente) {
        this.paciente = paciente;
    }
}
