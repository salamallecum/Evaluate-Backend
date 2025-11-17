package com.sistemaAcademico.sistema_academico_backend.repositorios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Categoria;
import com.sistemaAcademico.sistema_academico_backend.modelo.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Interfaz que define los metodos jpa para las transacciones de los examenes
@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    //Metodo que permite listar examenes que pertenecen a una categoria
    List<Examen> findByCategoria(Categoria categoria);

    //Metodo que permite consultar los examenes que se encuentran activos
    List<Examen> findByEstaActivo(boolean estado);

    //Metodo que permite filtrar los examenes que estan activos para una categoria en específica
    List<Examen> findByCategoriaAndEstaActivo(Categoria categoria, boolean estado);
}
