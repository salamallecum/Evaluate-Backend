package com.sistemaAcademico.sistema_academico_backend.servicios;

import com.sistemaAcademico.sistema_academico_backend.jwt.JwtUtils;
import com.sistemaAcademico.sistema_academico_backend.modelo.Usuario;
import com.sistemaAcademico.sistema_academico_backend.modelo.UsuarioRol;
import com.sistemaAcademico.sistema_academico_backend.repositorios.RolRepository;
import com.sistemaAcademico.sistema_academico_backend.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

//Clase servicio que implementa los metodos jpa de la clase UsuarioRepository
@Service
public class UsuarioService implements UserDetailsService {

    //Inyectamos un objeto de la interfaz usuarioRepository
    @Autowired
    private UsuarioRepository usuRepository;

    //Inyectamos un objeto de la interfaz rolRepository
    @Autowired
    private RolRepository rolRepository;

    //Inyectamos un objeto de la clase AuthenticationManagerBuilder
    @Autowired
    private AuthenticationManagerBuilder authManagerBuild;

    //Inyectamos un objeto JwtUtils
    @Autowired
    private JwtUtils jwtUtil;

    //Inyectamos el objeto que cifra contraseñas
    @Autowired
    private PasswordEncoder passEncod;

    //Metodo que registra un usuario en el sistema
    public ResponseEntity<?> guardarUsuario(Usuario nvoUsuario, Set<UsuarioRol> usuarioRoles) {

        //Validamos que el usuario no haya sido registrado con anterioridad
        if (usuRepository.existsByUsername(nvoUsuario.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
        } else {
            //Recorremos el o los roles asociados al nuevo usuario y los registramos en el sistema
            for (UsuarioRol usuarioRol : usuarioRoles) {
                rolRepository.save(usuarioRol.getRol());
            }
            nvoUsuario.getUsuarioRoles().addAll(usuarioRoles);
            //Ciframos la contraseña antes de guardarla en base de datos
            nvoUsuario.setPassword(passEncod.encode(nvoUsuario.getPassword()));
            usuRepository.save(nvoUsuario);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nvoUsuario);
    }

    //Metodo que permite obtener un usuario por su nombre de usuario
    public Usuario  obtenerUsuario(String username) {
        return usuRepository.findByUsername(username);
    }

    //Metodo que permite eliminar un usuario
    public void eliminarUsuario(Long idUsuario) {
        usuRepository.deleteById(idUsuario);
    }

    //Metodo que realiza la autenticación a partir del usuario y la constraseña
    public String autenticar(String username, String password){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult = authManagerBuild.getObject().authenticate(authToken);
        return jwtUtil.generateToken(authResult);
    }

    //Metodo que carga la informacion de un usuario teniendo en cuenta el nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuRepository.findByUsername(username);
        return usuario;
    }


}
