package com.sistemaAcademico.sistema_academico_backend.repositorios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Interfaz que define los metodos jpa para las transacciones de las categorias
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


}
