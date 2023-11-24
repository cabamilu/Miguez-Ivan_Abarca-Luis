package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.IOdontologoService;
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
@RequestMapping("/odontologos")
public class OdontologoController {
    private IOdontologoService iOdontologoService;

    public OdontologoController(IOdontologoService iOdontologoService) {
        this.iOdontologoService = iOdontologoService;
    }

    @Operation(summary = "Registra un nuevo odontólogo")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "El odontólogo fue agregado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<OdontologoSalidaDto> registrarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo){
        return new ResponseEntity<>(iOdontologoService.registrarOdontologo(odontologo), HttpStatus.CREATED);
    }

    @Operation(summary = "Búsqueda de un odontólogo por Id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El odontólogo fue obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoSalidaDto> buscarOdontologoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(iOdontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Listado de todos los odontólogos")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El listado de odontólogos fue obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos() {
        return new ResponseEntity<>(iOdontologoService.listarOdontologos(), HttpStatus.OK);
    }

    @Operation(summary = "Actualización de un odontólogo")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "El odontólogo fue actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @PutMapping("/actualizar")
    public ResponseEntity<OdontologoSalidaDto> actualizarOdontologo(@RequestBody @Valid OdontologoModificacionEntradaDto odontologo){
        return new ResponseEntity<>(iOdontologoService.actualizarOdontologo(odontologo), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de un odontólogo por Id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "El odontólogo fue eliminado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        iOdontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}
