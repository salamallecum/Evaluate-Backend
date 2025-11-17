package com.sistemaAcademico.sistema_academico_backend.controladores;

import com.sistemaAcademico.sistema_academico_backend.dtos.LoginUsuarioDto;
import com.sistemaAcademico.sistema_academico_backend.modelo.*;
import com.sistemaAcademico.sistema_academico_backend.servicios.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

//Clase controladora que contiene los endpoints para las transacciones tipo rest de los usuarios
@Tag(name = "Usuarios", description = "Transacciones usadas para la gestión de usuarios")
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200/") //Url en la que se encuentra el frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServ;

    //ETIQUETAS USADAS EN LA DOCUMENTACION
    //@Tag: Permite documentar el controlador
    //@Operation: Permite definir una descripción para la operación
    //@RequestBody: Permite documentar cuando un endpoint recibe un objeto como parametro
    //@ApiResponses: Permite documentar la forma en que una operación concreta responde, teniendo en cuenta las posibles respuestas en caso de error

    //EndPoint para registrar usuarios
    @Operation(summary = "Registrar usuario", description = "Registra un usuario en el sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Registro de un objeto de tipo Usuario",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el usuario y retorna la info del mismo.", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque esta mal armado el json del usuario")
    })
    @PostMapping("/registro")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario nvoUsuario)throws Exception{
        nvoUsuario.setPerfil("default.png");
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        Rol rol = new Rol();
        rol.setRolId(2L);
        rol.setNombre("NORMAL");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(nvoUsuario);
        usuarioRol.setRol(rol);

        //Agregamos el rol normal al ampleado para que se guarde tambien en base de datos
        usuarioRoles.add(usuarioRol);
        return usuarioServ.guardarUsuario(nvoUsuario, usuarioRoles);
    }

    //EndPoint para consultar un usuario por su nombre de usuario
    @Operation(summary = "Obtener usuario", description = "Obtiene un usuario teniendo en cuenta su nombre de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna la info del usuario"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque el usuario no existe.")
    })
    @GetMapping("/{nombreUsuario}")
    public Usuario obtenerUsuario(@PathVariable String nombreUsuario){
        return usuarioServ.obtenerUsuario(nombreUsuario);
    }

    //EndPoint para eliminar un usuario
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario teniendo en cuenta su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el usuario."),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque no existe usuario con ese id.")
    })
    @DeleteMapping("/{idUsuario}")
    public void eliminarUsuario(@PathVariable Long idUsuario){
        usuarioServ.eliminarUsuario(idUsuario);
    }

    //EndPoint que genera tokens para el inicio de sesion del usuario (logueo)
    @Operation(summary = "Login", description = "Genera tokens para el inicio de sesión del usuario en el sistema")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Valida la información suministrada por el objeto dto y si esta coincide con la info del usuario registrada, generará un token",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el token del usuario.", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque esta mal armado el json del usuario o el usuario no existe o están mal las credenciales suministradas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuarioDto loginUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Error - Revise sus credenciales");
        }
        try{
            String jwt = usuarioServ.autenticar(loginUserDto.username, loginUserDto.password);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error al autenticar, Revise sus credenciales" + e.getMessage()
            );
        }
    }

    //EndPoint que obtiene los datos de un usuario mediante el token generado al loguearse
    @Operation(summary = "Obtener info del usuario", description = "Obtiene la info del usuario logueado indicando si este ya está autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna si el usuario ya se encuentra autenticado"),
            @ApiResponse(responseCode = "401", description = "Retorna error 401 porque el usuario no existe o el usuario aún no se ha autenticado.")
    })
    @GetMapping("/actual-usuario")
    public Usuario obtenerUsuarioActual(Principal principal){
        return (Usuario) usuarioServ.loadUserByUsername(principal.getName());
    }

}
