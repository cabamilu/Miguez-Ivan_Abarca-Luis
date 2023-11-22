package com.backend.clinicaodontologica.dto.salida.turno;

import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TurnoSalidaDto {
    private Long id;
    private LocalDateTime fechaYHora;
    @JsonProperty("odontologo")
    private OdontologoSalidaDto odontologo;
    @JsonProperty("paciente")
    private PacienteSalidaDto paciente;

    public TurnoSalidaDto() {
    }
    public TurnoSalidaDto(Long id, LocalDateTime fechaYHora, OdontologoSalidaDto odontologo, PacienteSalidaDto paciente) {
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

    public OdontologoSalidaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoSalidaDto odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteSalidaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteSalidaDto paciente) {
        this.paciente = paciente;
    }
}
