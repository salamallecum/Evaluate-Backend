package com.sistemaAcademico.sistema_academico_backend.configuraciones;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

import java.util.List;

//Clase encargada de la configuracion e información principal de la aplicación que tendrá la documentación Swagger
@OpenAPIDefinition(
        info = @Info(
                //Info de la aplicacion e info de contacto del desarrollador
                title = "Sistema Academico Backend",
                description = "Api REST con la lógica de negocio del Sistema Académico.",
                termsOfService =  "www.alejoamaya.com/teminosDeServicio",
                version = "1.0.0",
                contact = @Contact(name = "Alejo Amaya", url = "https://miportafolioweb.free.nf/?i=2", email = "luisalejandroamayatorres@gmail.com"),
                license = @License(name = "Standard License", url = "www.alejoamaya.com/licencia" )
        ),
        security = @SecurityRequirement(name = "Security token") //Aqui definimos el esquema de seguridad que tendra la documentacion
)
//Define el esquema de seguridad de la documentacion
@SecurityScheme(
        name = "Security token",
        description = "Token de acceso de la API",
        type = SecuritySchemeType.HTTP, //Indica que estamos trabajando con tokens
        in = SecuritySchemeIn.HEADER,
        scheme = "Bearer",
        bearerFormat = "JWT"
)
public class DocumentacionConfig {

    //Bean encargado de corregir la url de acceso a la documentación
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new io.swagger.v3.oas.models.servers.Server().url("/academico/api/v1")));
    }
}

