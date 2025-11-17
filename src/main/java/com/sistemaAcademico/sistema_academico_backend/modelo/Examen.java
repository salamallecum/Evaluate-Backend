package com.sistemaAcademico.sistema_academico_backend.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//Clase que modela un objeto de tipo examen
@Entity //Entidad de base de datos
@Table(name= "examenes")
public class Examen {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Campo autoincrementable
    private Long  id;

    private String titulo;
    private String descripcion;
    private String puntosMaximos;
    private String numeroDePreguntas;
    private boolean estaActivo = false;

    //Categoria a la que pertenecera el examen (relacion muchos a uno) - Muchos examenes pertenecen a una categoria
    @ManyToOne(fetch = FetchType.EAGER) //Retornara todo lo relacionado
    private Categoria categoria;

    //Preguntas que corresponden a una categoria
    //Relacion uno a muchos (una categoria tiene muchas preguntas)
    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Pregunta> preguntas = new HashSet<>();

    //Constructores
    public Examen() {}

    public Examen(Long id, String titulo, String descripcion, String puntosMaximos, String numeroDePreguntas, boolean estaActivo, Categoria categoria, Set<Pregunta> preguntas) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.puntosMaximos = puntosMaximos;
        this.numeroDePreguntas = numeroDePreguntas;
        this.estaActivo = estaActivo;
        this.categoria = categoria;
        this.preguntas = preguntas;
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

    public String getPuntosMaximos() {
        return puntosMaximos;
    }

    public void setPuntosMaximos(String puntosMaximos) {
        this.puntosMaximos = puntosMaximos;
    }

    public String getNumeroDePreguntas() {
        return numeroDePreguntas;
    }

    public void setNumeroDePreguntas(String numeroDePreguntas) {
        this.numeroDePreguntas = numeroDePreguntas;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
