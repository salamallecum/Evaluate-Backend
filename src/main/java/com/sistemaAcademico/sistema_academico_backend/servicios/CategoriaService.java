package com.sistemaAcademico.sistema_academico_backend.servicios;

import com.sistemaAcademico.sistema_academico_backend.modelo.Categoria;
import com.sistemaAcademico.sistema_academico_backend.repositorios.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

//Clase servicio que implementa los metodos jpa de la clase categoriaRepository
@Service
public class CategoriaService {

    //Inyectamos un objeto categoriaRepository
    @Autowired
    private CategoriaRepository categoriaRepository;

    //Metodo que permite registrar una categoria
    public Categoria agregarCategoria(Categoria nvaCategoria){
        return  categoriaRepository.save(nvaCategoria);
    }

    //Metodo que permite actualizar una categoria
    public Categoria actualizarCategoria(Categoria categoriaEdit){
        return  categoriaRepository.save(categoriaEdit);
    }

    //Metodo que permite listar todas las categorias del sistema
    public Set<Categoria> obtenerCategorias(){
        return new LinkedHashSet<Categoria>(categoriaRepository.findAll());
    }

    //Metodo que permite obtener una categoria por su id
    public Categoria obtenerCategoria(Long idCategoria){
        return categoriaRepository.findById(idCategoria).get();
    }

    //Metodo que permite eliminar una categoria
    public void eliminarCategoria(Long idCategoria){
        Categoria categoriaAEliminar = new  Categoria();
        categoriaAEliminar.setId(idCategoria);
        categoriaRepository.delete(categoriaAEliminar);
    }
}
