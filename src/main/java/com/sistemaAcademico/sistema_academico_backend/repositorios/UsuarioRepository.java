package com.sistemaAcademico.sistema_academico_backend.repositorios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Interfaz que define los metodos jpa para las transacciones de los usuarios
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Metodo para consultar un usuario por su nombre de usuario
    Usuario findByUsername(String username);

    //Metodo que indica si existe o no un nombre de usuario
    boolean existsByUsername(String username);
}

