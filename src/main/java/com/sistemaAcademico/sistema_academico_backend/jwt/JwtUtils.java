package com.sistemaAcademico.sistema_academico_backend.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Clase componente que define los metodos que permiten el manejo de peticiones JWT, tokens y demás
@Service
public class JwtUtils {

    //Definimos la clave secreta que usara jwt para crear los token
    //(Esta definida en el archivo application.properties)
    @Value("${jwt.secret}")
    private String secret;

    //Definimos el tiempo de expiracion del token (tambien esta definido en el archivo application.properties)
    @Value("${jwt.expiration}")
    private int expiration;

    //Metodo que genera los token
    public String generateToken(Authentication auth){
        UserDetails mainUser = (UserDetails) auth.getPrincipal();
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        //Construimos el token y lo retornamos
        return Jwts.builder().setSubject(mainUser.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L)) //3600*1000=3600000 (Cada token generado expirará en una hora)
                .signWith(key)
                .compact();
    }

    //Metodo que valida si la info de un token ccoincide con la info registrada del usuario en el sistema
    // y valida si el token no ha expirado
    public boolean validateToken(String token, UserDetails userDet){
        final String username = extractUserName(token);
        return (username.equals(userDet.getUsername()) && !isTokenExpired(token));
    }

    //Metodo que valida si un token ya expiró
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //Metodo que le extrae la fecha de expiracion a un token
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    //Metodo que obtiene todas las afirmaciones de un token
    public Claims extractAllClaims(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        //metodo parseClaimsJws() -> para tokens firmados o Json web signature JWS (aquellos token que tienen encabezado, payload y firma)
        //metodo parseClaimsJwt() -> para tokens no firmados (aquellos token que tienen encabezado y payload)
    }

    //Metodo que obtiene los datos de un usuario mediante el token generado al loguearse
    public String extractUserName(String token){
        return extractAllClaims(token).getSubject();
    }
}
