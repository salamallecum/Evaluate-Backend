package com.sistemaAcademico.sistema_academico_backend.repositorios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Examen;
import com.sistemaAcademico.sistema_academico_backend.modelo.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

//Interfaz que define los metodos jpa para las transacciones de las preguntas
@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    //Metodo que retorna las preguntas que estan asociadas a un examen
    Set<Pregunta> findByExamen(Examen examen);
}
