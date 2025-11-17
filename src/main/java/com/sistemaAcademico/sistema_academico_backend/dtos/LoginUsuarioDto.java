package com.sistemaAcademico.sistema_academico_backend.dtos;

public class LoginUsuarioDto {

    //Atributos
    public String username;
    public String password;

    //Constructores
    public LoginUsuarioDto() {}

    public LoginUsuarioDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Metodos
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
