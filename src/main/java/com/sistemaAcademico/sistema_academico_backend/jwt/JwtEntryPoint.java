package com.sistemaAcademico.sistema_academico_backend.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Clase componente que se encarga de manejar los errores quep uedan surgir al momento en que desee loguearse un usuario
public class JwtEntryPoint implements AuthenticationEntryPoint {

    //Metodo que devuelve un error en el jwtResponse cuando el usuario no esté autenticado correctamente
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no autorizado");
    }
}
