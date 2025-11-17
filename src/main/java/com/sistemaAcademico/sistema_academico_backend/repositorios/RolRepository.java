package com.sistemaAcademico.sistema_academico_backend.repositorios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Interfaz que define los metodos jpa para las transacciones de los roles
@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
}
