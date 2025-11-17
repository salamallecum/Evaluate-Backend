package com.sistemaAcademico.sistema_academico_backend.jwt;

import com.sistemaAcademico.sistema_academico_backend.servicios.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Partes de un token
//Encabezado(Header): Contiene metadatos sobre el token y el algoritmo de encriptamiento que puede ser sha256
//Carga útil(Payload): Contiene las reclamaciones o declaraciones de una entidad (Generalmente el nombre de usuario y datos adicionales)
//Firma(Signature): Esta compuesto por el encabezado, carga util y una clave secreta, todo esta encriptado en conjunto mediante el algoritmo de encriptamiento definido en el encabezado


//Clase componente que modela el filtro de autenticacion utilizado por JWT
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Inyectamos un objeto de la clase JwtUtils
    @Autowired
    private JwtUtils jwtUtils;

    //Inyectamos un objeto de tipo UserDetailsService
    @Autowired
    private UsuarioService userService;

    //Constructor sin argumentos
    public JwtAuthenticationFilter() {}

    //Metodo que define lo que hará internamente el filtro cuando le llegan peticiones JWT o tokens
    //La anotacion nonNull nos indica que los parametros del metodo no serán nulos en ningun momento
    //Metodo que define el comportamiento del filtro de autenticación
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt  = null;

        //Validamos si el encabezado del token existe y tiene la palabra bearer
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //Extraemos el contenido del token
            jwt = authHeader.substring(7);
            //Extraemos el nombre de usuario del token
            username = jwtUtils.extractUserName(jwt);
        }

        //Validamos que existe un nombre de usuario y un proceso de autenticación
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //Obtenemos la info del usuario registrada en sistema
            UserDetails usrDet = userService.loadUserByUsername(username);

            //Validamos que el token coincida con lo registrado en sistema y procedemos a loguearlo
            if(jwtUtils.validateToken(jwt, usrDet)){
                UsernamePasswordAuthenticationToken userNameAuthToken = new UsernamePasswordAuthenticationToken(usrDet, null, usrDet.getAuthorities());
                userNameAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userNameAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}