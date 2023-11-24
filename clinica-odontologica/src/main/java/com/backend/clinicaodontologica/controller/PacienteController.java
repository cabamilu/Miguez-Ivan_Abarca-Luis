package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.IPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private IPacienteService iPacienteService;

    public PacienteController(IPacienteService iPacienteService) {
        this.iPacienteService = iPacienteService;
    }


    @Operation(summary = "Registra un nuevo pciente")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "El Paciente fue agregado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid PacienteEntradaDto paciente){
        return new ResponseEntity<>(iPacienteService.registrarPaciente(paciente), HttpStatus.CREATED);
    }

    @Operation(summary = "Búsqueda de un paciente por Id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El paciente fue obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDto> buscarPacientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(iPacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Listado de todos los pacientes")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El listado de pacientes fue obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PacienteSalidaDto>> listarPacientes() {
        return new ResponseEntity<>(iPacienteService.listarPacientes(), HttpStatus.OK);
    }

    @Operation(summary = "Actualización de un paciente")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El paciente fue actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PutMapping("/actualizar")
    public ResponseEntity<PacienteSalidaDto> actualizarPaciente(@RequestBody @Valid PacienteModificacionEntradaDto paciente){
        return new ResponseEntity<>(iPacienteService.actualizarPaciente(paciente), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de un paciente por Id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "El paciente fue eliminado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        iPacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
