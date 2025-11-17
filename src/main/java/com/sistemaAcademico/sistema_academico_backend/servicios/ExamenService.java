package com.sistemaAcademico.sistema_academico_backend.servicios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Categoria;
import com.sistemaAcademico.sistema_academico_backend.modelo.Examen;
import com.sistemaAcademico.sistema_academico_backend.repositorios.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//Clase servicio que implementa los metodos jpa de la clase ExamenRepository
@Service
public class ExamenService {

    //Inyectamos el objeto ExamenRepository
    @Autowired
    private ExamenRepository examRepository;

    //Metodo que permite registrar un nuevo examen
    public Examen agregarExamen(Examen nvoExamen){
        return examRepository.save(nvoExamen);
    }

    //Metodo que permite editar un examen
    public Examen actualizarExamen(Examen examenEdit){
        return examRepository.save(examenEdit);
    }

    //Metodo que permite listar los examenes existentes en el sistema
    public Set<Examen> listarExamenes(){
        return new LinkedHashSet<Examen>(examRepository.findAll());
    }

    //Metodo que permite obtener la info de un examen por su id
    public Examen obtenerExamen(Long idExamen){
        return examRepository.findById(idExamen).get();
    }

    //Metodo que permite eliminar un examen
    public void eliminarExamen(Long idExamen){
        Examen examen = new Examen();
        examen.setId(idExamen);
        examRepository.delete(examen);
    }

    //Metodo que permite listar los examenes que pertenecen a una categoria particular
    public List<Examen> obtenerExamenesDeUnaCategoria(Categoria categoriaAConsultar){
       return examRepository.findByCategoria(categoriaAConsultar);
    }

    //Metodo que permite obtener todos los examanes que estan activos
    public List<Examen> obtenerExamenesActivos(){
        return examRepository.findByEstaActivo(true);
    }

    //Metodo que permite obtener los examenes que estan activos para una categoria en especifica
    public List<Examen> obtenerExamenesActivosDeUnaCategoria(Categoria categoriaAConsultar){
        return examRepository.findByCategoriaAndEstaActivo(categoriaAConsultar, true);
    }

}
