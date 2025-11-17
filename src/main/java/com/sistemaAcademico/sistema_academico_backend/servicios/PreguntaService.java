package com.sistemaAcademico.sistema_academico_backend.servicios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Examen;
import com.sistemaAcademico.sistema_academico_backend.modelo.Pregunta;
import com.sistemaAcademico.sistema_academico_backend.repositorios.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

//Clase servicio que implementa los metodos jpa de la clase preguntaRepository
@Service
public class PreguntaService {

    //Inyectamos el objeto preguntaRepository
    @Autowired
    private PreguntaRepository preguntaRepository;

    //Metodo que permite registrar una pregunta
    public Pregunta agregarPregunta(Pregunta nvaPregunta){
        return preguntaRepository.save(nvaPregunta);
    }

    //Metodo que permite actualizar una pregunta
    public Pregunta actualizarPregunta(Pregunta preguntaEdit){
        return preguntaRepository.save(preguntaEdit);
    }

    //Metodo que permite listar las preguntas existentes en el sistema
    public Set<Pregunta> listarPreguntas(){
        return new LinkedHashSet<Pregunta>(preguntaRepository.findAll());
    }

    //Metodo que permite consultar una pregunta
    public Pregunta obtenerPregunta(Long idPregunta){
        return preguntaRepository.findById(idPregunta).get();
    }

    //Metodo que permite eliminar una pregunta
    public void eliminarPregunta(Long id){
        Pregunta preguntaEliminar = new Pregunta();
        preguntaEliminar.setId(id);
        preguntaRepository.delete(preguntaEliminar);
    }

    //Metodo que permite obtener las preguntas que pertenecen a un examen
    public Set<Pregunta> obtenerPreguntasDelExamen(Examen examen){
        return new LinkedHashSet<Pregunta>(preguntaRepository.findByExamen(examen));
    }

    //Metodo que obtener una pregunta para hacer su respectiva evaluacion
    public Pregunta listarPregunta(Long idPregunta){
       return preguntaRepository.getOne(idPregunta);
    }
}
