package com.sistemaAcademico.sistema_academico_backend.modelo;

import jakarta.persistence.*;

//Clase que modela un objeto de tipo pregunta
@Entity //Entidad base de datos
@Table(name= "preguntas")
public class Pregunta {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Campo autoincrementable
    private Long id;

    @Column(length = 5000) //longitus de 5000 caracteres
    private String contenido;

    private String imagen;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;
    private String respuesta;

    @Transient
    private String respuestaDada;

    //Relacion muchos a uno (Muchas preguntas pueden pertenecer a un examen)
    @ManyToOne(fetch = FetchType.EAGER) //Va a retornar todo lo reelacionado
    private Examen examen;

    //Constructores
    public Pregunta() {}

    public Pregunta(Long id, String contenido, String imagen, String opcion1, String opcion2, String opcion3, String opcion4, String respuesta, Examen examen) {
        this.id = id;
        this.contenido = contenido;
        this.imagen = imagen;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.respuesta = respuesta;
        this.examen = examen;
    }

    //Metodos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public String getRespuestaDada() {
        return respuestaDada;
    }

    public void setRespuestaDada(String respuestaDada) {
        this.respuestaDada = respuestaDada;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
