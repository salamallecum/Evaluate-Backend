package com.sistemaAcademico.sistema_academico_backend.controladores;

import com.sistemaAcademico.sistema_academico_backend.modelo.Examen;
import com.sistemaAcademico.sistema_academico_backend.modelo.Pregunta;
import com.sistemaAcademico.sistema_academico_backend.servicios.ExamenService;
import com.sistemaAcademico.sistema_academico_backend.servicios.PreguntaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//Clase controladora que contiene los endpoints para las transacciones tipo rest de las preguntas
@Tag(name = "Preguntas", description = "Transacciones usadas para la gestión de preguntas")
@RestController
@RequestMapping("/preguntas")
@CrossOrigin(origins = "http://localhost:4200/") //Url en la que se encuentra el frontend
public class PreguntaController {

    //Inyectamos un objeto del tipo Preguntaservice
    @Autowired
    private PreguntaService preguntaService;

    //Inyectamos un objeto del tipo examenService
    @Autowired
    private ExamenService examenService;

    //ETIQUETAS USADAS EN LA DOCUMENTACION
    //@Tag: Permite documentar el controlador
    //@Operation: Permite definir una descripción para la operación
    //@RequestBody: Permite documentar cuando un endpoint recibe un objeto como parametro
    //@ApiResponses: Permite documentar la forma en que una operación concreta responde, teniendo en cuenta las posibles respuestas en caso de error

    //EndPoint que permite registrar una pregunta
    @Operation(summary = "Registrar pregunta", description = "Registra una pregunta en el sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Registro de un objeto de tipo pregunta",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pregunta.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra la pregunta y retorna la info de la misma.", content = @Content(schema = @Schema(implementation = Pregunta.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque esta mal armado el json de la pregunta")
    })
    @PostMapping
    public ResponseEntity<?>registrarPregunta(@RequestBody Pregunta nvaPregunta){
        Pregunta pregGuardada =  preguntaService.agregarPregunta(nvaPregunta);
        return ResponseEntity.ok(pregGuardada);
    }

    //EndPoint que permite listar las preguntas registradas
    @Operation(summary = "Obtener preguntas", description = "Obtiene el listado de preguntas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de preguntas", content = @Content(schema = @Schema(implementation = Pregunta.class)))
    })
    @GetMapping
    public ResponseEntity<?>listarPreguntas(){
        return ResponseEntity.ok(preguntaService.listarPreguntas());
    }

    //EndPoint que permite actualizar una pregunta
    @Operation(summary = "Actualizar pregunta", description = "Actualiza la información de una pregunta")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Actualización de un objeto de tipo pregunta",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pregunta.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualiza la pregunta y retorna la info de la misma.", content = @Content(schema = @Schema(implementation = Pregunta.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque la pregunta a actualizar no existe o esta mal armado el json de la pregunta.")
    })
    @PutMapping
    public Pregunta actualizarPregunta(@RequestBody Pregunta preguntaEdit){
        return preguntaService.actualizarPregunta(preguntaEdit);
    }

    //EndPoint que permite consultar una pregunta por su id
    @Operation(summary = "Obtener pregunta", description = "Obtiene una pregunta teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna la info de la pregunta"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque la pregunta no existe.")
    })
    @GetMapping("/{idPregunta}")
    public Pregunta obtenerPregunta(@PathVariable Long idPregunta){
        return preguntaService.obtenerPregunta(idPregunta);
    }

    //EndPoint que permite eliminar una pregunta
    @Operation(summary = "Eliminar pregunta", description = "Elimina una pregunta teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la pregunta."),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque no existe pregunta con ese id.")
    })
    @DeleteMapping("/{idPregunta}")
    public void eliminarPregunta(@PathVariable Long idPregunta){
        preguntaService.eliminarPregunta(idPregunta);
    }

    //EndPoint que lista las preguntas que pertenecen a un examen
    @Operation(summary = "Listar preguntas x Examen", description = "Consulta el listado de preguntas que pertenecen a un examen teniendo en cuenta el id del examen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de preguntas que pertenecen al id del examen brindado.", content = @Content(schema = @Schema(implementation = Pregunta.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque no existen preguntas asociadas al id de examen suministrado.")
    })
    @GetMapping("/examen/{idExamen}")
    public ResponseEntity<?> listarPreguntasDeUnExamen(@PathVariable Long idExamen){
        Examen examenConsultado = examenService.obtenerExamen(idExamen);
        Set<Pregunta> preguntasDelExamen = examenConsultado.getPreguntas();

        List listaDeExamenes = new ArrayList(preguntasDelExamen);
        //Validamos que el examen tenga preguntas
        if(listaDeExamenes.size() > Integer.parseInt(examenConsultado.getNumeroDePreguntas())){
            listaDeExamenes = listaDeExamenes.subList(0, Integer.parseInt(examenConsultado.getNumeroDePreguntas() + 1));
        }

        Collections.shuffle(listaDeExamenes);
        return ResponseEntity.ok(listaDeExamenes);
    }

    //Metodo que lista las preguntas de un examen con sus respectivas respuestas para el usuario tipo admin
    @Operation(summary = "Listar preguntas de un examen con respuestas", description = "Consulta el listado de preguntas que pertenecen a un examen teniendo en cuenta el id del examen e incluyendo sus respuestas para que sean consultados por el administrador de sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el listado de preguntas que pertenecen al id del examen brindado.", content = @Content(schema = @Schema(implementation = Pregunta.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque no existen preguntas asociadas al id de examen suministrado.")
    })
    @GetMapping("/examen/todos/{idExamen}")
    public ResponseEntity<?> listarPreguntasDelExamenComoAdministrador(@PathVariable Long idExamen){
        Examen examen = new Examen();
        examen.setId(idExamen);
        Set<Pregunta> preguntas = preguntaService.obtenerPreguntasDelExamen(examen);
        return ResponseEntity.ok(preguntas);
    }

    //EndPoint que permite evaluar las preguntas de un examen
    @Operation(summary = "Evaluar preguntas de examen", description = "Evalua las preguntas de un examen")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Listado de preguntas de un examen",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pregunta.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna eel resultado de la evaluacion mapeado en un objeto hashMap.", content = @Content(schema = @Schema(implementation = Pregunta.class))),
    })
    @PostMapping("/evaluar-examen")
    public ResponseEntity<?> evaluarExamen(@RequestBody List<Pregunta> preguntas){
        double puntosMaximos = 0;
        Integer respuestaCorrectas = 0;
        Integer intentos = 0;

        //Recorremos toda la lista de preguntas
        for (Pregunta p : preguntas) {
            Pregunta pregunta = preguntaService.listarPregunta(p.getId());
            //Validamos si la respuesta dad por el usuario es igual a la respuesta corrrecta
            if(pregunta.getRespuesta().equals(p.getRespuestaDada())){
                respuestaCorrectas++;
                double puntos = Double.parseDouble(preguntas.get(0).getExamen().getPuntosMaximos()) / preguntas.size();
                puntosMaximos = puntosMaximos + puntos;
            }
            //Si la respuest dada es incorrecta pero es diferente de null, solo se cuenta como intento
            if(p.getRespuestaDada() != null){
                intentos++;
            }
        }
        //Mapeamos la respuesta del servidor con los resultados de la evaluación del examen
        Map<String, Object> respuestas = new HashMap<>();
        respuestas.put("puntosMaximos", puntosMaximos);
        respuestas.put("respuestaCorrectas", respuestaCorrectas);
        respuestas.put("intentos", intentos);
        return ResponseEntity.ok(respuestas);
    }
}
