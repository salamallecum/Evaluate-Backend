package com.sistemaAcademico.sistema_academico_backend.modelo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity //Esta clase representa una entidad de base de datos
@Table(name = "roles") //Indicamos que cree una tabla en la bd llamada roles
public class Rol {

    //Atributos
    @Id
    private Long rolId;
    private String nombre;

    //Definimos la relacion de que un rol podra ser asociado a multiples usuarios muchos roles con su respectivas restricciones en caso de que el usuario se actualice o elimine
    //LAZY(Perezoso): Con esa propiedad indicamos que solo retorne la info del rol no los registros en donde esta relacionado (eso lo retornará si se crea un metodo adicional)
    //Mapped by: Indica cual es la entidad dueña de la relacion (que en este caso es el rol)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    //Constructor
    public Rol() {
    }

    //Metodos
    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<UsuarioRol> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }
}
