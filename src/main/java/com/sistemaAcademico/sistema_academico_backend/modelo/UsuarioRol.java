package com.sistemaAcademico.sistema_academico_backend.modelo;

import jakarta.persistence.*;

@Entity
//Esta clase indica la relacion que tiene cada usuario con el rol en el sistema
public class UsuarioRol {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioRolId;

    @ManyToOne(fetch = FetchType.EAGER) //Indicamos que el atributo forma parte de una relacion uno a muchos
    private Usuario usuario; //Muchos roles le pueden pertenecer a un usuario

    @ManyToOne
    private Rol rol;

    //Constructor
    public UsuarioRol() {
    }

    //Metodos
    public Long getUsuarioRolId() {
        return usuarioRolId;
    }

    public void setUsuarioRolId(Long usuarioRolId) {
        this.usuarioRolId = usuarioRolId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
