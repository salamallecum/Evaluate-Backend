package com.sistemaAcademico.sistema_academico_backend.modelo;

//Clase modelo que define el objeto que lleva el token de acceso generado por el sistema
public class JwtResponse {
    //Atributo
    private String token;

    //Constructores
    public JwtResponse() {}

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
