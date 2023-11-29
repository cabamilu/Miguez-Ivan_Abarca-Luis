package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.ITurnoService;
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
@RequestMapping("/turnos")
@CrossOrigin
public class TurnoController {
    ITurnoService iTurnoService;

    public TurnoController(ITurnoService iTurnoService) {
        this.iTurnoService = iTurnoService;
    }

    @Operation(summary = "Registra un nuevo turno")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "El turno fue agregado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TurnoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarTurno(@RequestBody @Valid TurnoEntradaDto turno) throws BadRequestException {
        return new ResponseEntity<>(iTurnoService.registrarTurno(turno), HttpStatus.CREATED);
    }

    @Operation(summary = "Búsqueda de un turno por Id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El turno fue obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TurnoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(iTurnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Listado de todos los turnos")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El listado de turnos fue obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TurnoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos() {
        return new ResponseEntity<>(iTurnoService.listarTurnos(), HttpStatus.OK);
    }

    @Operation(summary = "Actualización de un turno")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El turno fue actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TurnoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PutMapping("/actualizar")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoModificacionEntradaDto turno){
        return new ResponseEntity<>(iTurnoService.actualizarTurno(turno), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de un turno por Id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "El turno fue eliminado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado(s)"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        iTurnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}
