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
    @FutureOrPresent(message = "La fecha y hora no pueden ser anterior a la del día de hoy")
    @NotNull(message = "Debe especificarse la fecha y hora del turno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El odontologo del turno no puede ser nulo")
    @JsonProperty("odontologo")
    @Valid
    private OdontologoModificacionEntradaDto odontologo;
    @NotNull(message = "El paciente del turno no puede ser nulo")
    @JsonProperty("paciente")
    @Valid
    private PacienteModificacionEntradaDto paciente;

    public TurnoModificacionEntradaDto() {
    }
    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, OdontologoModificacionEntradaDto odontologo, PacienteModificacionEntradaDto paciente) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
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

    public OdontologoModificacionEntradaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoModificacionEntradaDto odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteModificacionEntradaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModificacionEntradaDto paciente) {
        this.paciente = paciente;
    }
}
