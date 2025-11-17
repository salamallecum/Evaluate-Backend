package com.sistemaAcademico.sistema_academico_backend.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

//Clase que modela un objeto de tipo categoria
@Entity //Entidad de base de datos
@Table(name= "categorias")
public class Categoria {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Campo autoincrementable
    private Long id;

    private String titulo;
    private String descripcion;

    //Set de examenes que pertenecen a la categoria (relacion uno a muchos) una categoria muchos examenes
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Examen> examenes = new LinkedHashSet<>();

    //Constructores
    public Categoria() {}

    public Categoria(Long id, String titulo, String descripcion, Set<Examen> examenes) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.examenes = examenes;
    }

    //Metodos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(Set<Examen> examenes) {
        this.examenes = examenes;
    }
}
