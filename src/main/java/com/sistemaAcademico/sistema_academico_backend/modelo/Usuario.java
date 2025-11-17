package com.sistemaAcademico.sistema_academico_backend.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity //Esta clase representa una entidad de base de datos
@Table(name = "usuarios") //Indicamos que cree una tabla en la bd llamada usuarios
public class Usuario implements UserDetails{ //Implementaremos mettodos de la clase UserDetails

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que el id sera autoincrementable
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false) //Indicamos que el nombre de usuario va a ser unico
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private boolean estaActivo = true;
    private String perfil;

    //Definimos la relacion de que un usuario podra tener muchos roles con su respectivas restricciones en caso de que el usuario se actualice o elimine
    //EAGER(Ansioso): Con esa propiedad indiamos que retorne todos los registros a los que se encuentre vinculada una entidad (en este caso el usuario)
    //Mapped by: Indica cual es la entidad dueña de la relacion (que en este caso es el usuario)
    //el set albergara los roles que podran asociarse al usuario
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    @JsonIgnore //Esta anotacion nos permite acotar el resultado de la consulta de un usuario por username en postman y no salga resultado infinito
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    //Constructores
    public Usuario() {
    }

    public Usuario(Long id, String username, String password, String nombres, String apellidos, String email, String telefono, boolean estaActivo, String perfil) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.estaActivo = estaActivo;
        this.perfil = perfil;
    }

    //Metodos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        this.usuarioRoles.forEach(usuarioRol -> {
            autoridades.add(new Authority(usuarioRol.getRol().getNombre()));
        });
        return autoridades;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Set<UsuarioRol> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }
}
