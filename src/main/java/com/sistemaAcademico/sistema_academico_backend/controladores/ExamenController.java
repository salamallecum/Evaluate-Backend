package com.sistemaAcademico.sistema_academico_backend.controladores;

import com.sistemaAcademico.sistema_academico_backend.modelo.Categoria;
import com.sistemaAcademico.sistema_academico_backend.modelo.Examen;
import com.sistemaAcademico.sistema_academico_backend.servicios.ExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Clase controladora que contiene los endpoints para las transacciones tipo rest de los examenes
@Tag(name = "Examenes", description = "Transacciones usadas para la gestión de examenes")
@RestController
@RequestMapping("/examenes")
@CrossOrigin(origins = "http://localhost:4200/") //Url en la que se encuentra el frontend
public class ExamenController {

    //Inyectamos un obejto de tipo ExamenService
    @Autowired
    private ExamenService examenService;

    //ETIQUETAS USADAS EN LA DOCUMENTACION
    //@Tag: Permite documentar el controlador
    //@Operation: Permite definir una descripción para la operación
    //@RequestBody: Permite documentar cuando un endpoint recibe un objeto como parametro
    //@ApiResponses: Permite documentar la forma en que una operación concreta responde, teniendo en cuenta las posibles respuestas en caso de error

    //EndPoint que permite registrar un examen
    @Operation(summary = "Registrar examen", description = "Registra un examen en el sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Registro de un objeto de tipo examen",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Examen.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el examen y retorna la info de la misma.", content = @Content(schema = @Schema(implementation = Examen.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque esta mal armado el json del examen")
    })
    @PostMapping
    public ResponseEntity<?> registrarExamen(@RequestBody Examen nvoExamen){
        Examen examGuardo = examenService.agregarExamen(nvoExamen);
        return ResponseEntity.ok(examGuardo);
    }

    //EndPoint que permite listar los examenes registrados
    @Operation(summary = "Obtener examenes", description = "Obtiene el listado de examenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de examenes", content = @Content(schema = @Schema(implementation = Examen.class)))
    })
    @GetMapping
    public ResponseEntity<?> listarExamenes(){
        return ResponseEntity.ok(examenService.listarExamenes());
    }

    //EndPoint que permite actualizar un examen
    @Operation(summary = "Actualizar examen", description = "Actualiza la información de un examen")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Actualización de un objeto de tipo examen",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Examen.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualiza el examen y retorna la info del mismo.", content = @Content(schema = @Schema(implementation = Examen.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque el examen a actualizar no existe o esta mal armado el json del examen")
    })
    @PutMapping
    public Examen actualizarExamen(@RequestBody Examen examEdit){
        return examenService.actualizarExamen(examEdit);
    }

    //EndPoint que permite consultar un examen por su id
    @Operation(summary = "Obtener examen", description = "Obtiene un examen teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna la info del examen"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque el examen no existe")
    })
    @GetMapping("/{idExamen}")
    public Examen obtenerExamen(@PathVariable Long idExamen){
        return examenService.obtenerExamen(idExamen);
    }

    //EndPoint que permite eliminar un examen
    @Operation(summary = "Eliminar examen", description = "Elimina un examen teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el examen."),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque no existe examen con ese id")
    })
    @DeleteMapping("/{idExamen}")
    public void eliminarExamen(@PathVariable Long idExamen){
        examenService.eliminarExamen(idExamen);
    }

    //EndPoint que permite consultar los examenes que pertenecen a una categoria particular
    @Operation(summary = "Obtener examenes de una categoría", description = "Obtiene el listado de examenes que pertnecen a una categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de examenes"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque la categoria no existe y no puede consultar sus examenes")
    })
    @GetMapping("/categoria/{idCategoria}")
    public List<Examen> listarExamenesDeUnaCategoria(@PathVariable Long idCategoria){
        Categoria categoria = new Categoria();
        categoria.setId(idCategoria);
        return examenService.obtenerExamenesDeUnaCategoria(categoria);
    }

    //EndPoint que permite obtener todos los examanes que estan activos
    @Operation(summary = "Obtener examenes activos", description = "Obtiene el listado de todos los examenes que se encuentran activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de examenes activos", content = @Content(schema = @Schema(implementation = Examen.class)))
    })
    @GetMapping("/activo")
    public List<Examen> listarExamenesActivos(){
        return examenService.obtenerExamenesActivos();
    }

    //EndPoint que permite obtener los examenes que estan activos para una categoria en especifica
    @Operation(summary = "Obtener examenes activos de una categoría", description = "Obtiene el listado de examenes activos que pertenecen a una categoria especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de examenes activos de la categoria"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque la categoria no existe y no puede consultar sus examenes activos")
    })
    @GetMapping("/categoria/activo/{idCategoria}")
    public List<Examen> listarExamenesActivosDeUnaCategoria(@PathVariable Long idCategoria){
        Categoria categoria = new Categoria();
        categoria.setId(idCategoria);
        return examenService.obtenerExamenesActivosDeUnaCategoria(categoria);
    }
}
