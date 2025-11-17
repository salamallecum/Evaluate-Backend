package com.sistemaAcademico.sistema_academico_backend.controladores;

import com.sistemaAcademico.sistema_academico_backend.modelo.Categoria;
import com.sistemaAcademico.sistema_academico_backend.servicios.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Clase controladora que contiene los endpoints para las transacciones tipo rest de las categorias
@Tag(name = "Categorias", description = "Transacciones usadas para la gestión de categorias")
@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200/") //Url en la que se encuentra el frontend
public class CategoriaController {

    //Inyectamos un objeto de tipo CategoriaService
    @Autowired
    private CategoriaService categoriaService;

    //ETIQUETAS USADAS EN LA DOCUMENTACION
    //@Tag: Permite documentar el controlador
    //@Operation: Permite definir una descripción para la operación
    //@RequestBody: Permite documentar cuando un endpoint recibe un objeto como parametro
    //@ApiResponses: Permite documentar la forma en que una operación concreta responde, teniendo en cuenta las posibles respuestas en caso de error

    //EndPoint que permite el registro de categorias
    @Operation(summary = "Registrar categoría", description = "Registra una categoría en el sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Registro de un objeto de tipo categoria",
                                                            required = true,
                                                            content = @Content(
                                                                            mediaType = "application/json",
                                                                            schema = @Schema(implementation = Categoria.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra la categoria y retorna la info de la misma.", content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque esta mal armado el json de la categoria")
    })
    @PostMapping
    public ResponseEntity<?> registrarCategoria(@RequestBody Categoria nvaCategoria){
       Categoria catGuardada = categoriaService.agregarCategoria(nvaCategoria);
       return ResponseEntity.ok(catGuardada);
    }

    //EndPoint que permite consultar una categoria
    @Operation(summary = "Obtener categoría", description = "Obtiene una categoria teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna la info de la categoria"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque la categoria no existe")
    })
    @GetMapping("/{idCategoria}")
    public Categoria obtenerCategoria(@PathVariable Long idCategoria){
        return categoriaService.obtenerCategoria(idCategoria);
    }

    //EndPoint que permite listar las categorias existentes en el sistema
    @Operation(summary = "Obtener categorías", description = "Obtiene el listado de categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de categorias", content = @Content(schema = @Schema(implementation = Categoria.class)))
    })
    @GetMapping
    public ResponseEntity<?> listarCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());
    }

    //EndPoint que permite actualizar una categoria
    @Operation(summary = "Actualizar categoria", description = "Actualiza la información de una categoria")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Actualización de un objeto de tipo categoria",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Categoria.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualiza la categoria y retorna la nueva info de la misma.", content = @Content(schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque la categoria a actualizar no existe o esta mal armado el json de la categoria")
    })
    @PutMapping
    public Categoria actualizarCategoria(@RequestBody Categoria categoriaEdit){
        return categoriaService.actualizarCategoria(categoriaEdit);
    }

    //EndPoint que permite eliminar una categoria
    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la categoria."),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque no existe categoria con ese id")
    })
    @DeleteMapping("/{idCategoria}")
    public void eliminarCategoria(@PathVariable Long idCategoria){
        categoriaService.eliminarCategoria(idCategoria);
    }
}
