package com.sistemaAcademico.sistema_academico_backend.configuraciones;

import com.sistemaAcademico.sistema_academico_backend.jwt.JwtAuthenticationFilter;
import com.sistemaAcademico.sistema_academico_backend.jwt.JwtEntryPoint;
import com.sistemaAcademico.sistema_academico_backend.servicios.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//Clase que define las configuraciones de seguridad del backend
@Configuration
@EnableWebSecurity//Habilita la configuracion de seguridad
public class MySecurityConfig {

    @Bean
    //Metodo que define las configuraciones del filtro de seguridad
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/usuarios/login", "/usuarios/registro", "/v3/api-docs/**", "/swagger-ui/**").permitAll() //en patterns definimos las rutas a las que podremos acceder en nuestro sistema sin necesidad de autenticacion, lo que no esté ahi requerira autenticación
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint()))
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //Metodo que contruye el objeto JwtAuthenticationFilter para su uso en la configuracion del filtro de seguridad
    @Bean
    public JwtAuthenticationFilter jwtTokenFilter(){
        return new JwtAuthenticationFilter();
    }

    //Metodo que contruye el objeto JwtEntryPoint para su uso en la configuracion del filtro de seguridad
    @Bean
    public JwtEntryPoint jwtEntryPoint(){
        return new JwtEntryPoint();
    }

    //Metodo que nos permite cifrar la contraseña
    @Bean
    public PasswordEncoder passEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Metodo que contruye el objeto UsuarioService para su uso en la configuracion del filtro de seguridad
    @Bean
    public UserDetailsService userService(){
        return new UsuarioService();
    }

    //Metodo encargado de proveer y permite el proceso de autenticacion del usuario
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService());
        authProvider.setPasswordEncoder(passEncoder());
        return authProvider;
    }

    //Metodo que configura las transacciones y clientes que va a permitir la apliacion (las transacciones http y el cliente front se parametrizan aqui para permitir su acceso)
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); //url del cliente permitida (la usada por angular)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); //transacciones http permitidas
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); //encabezados de los token permitidos
        config.setAllowCredentials(true); //Permite el acceso mediante clave

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}