package br.com.alura.adopet.api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Adopet API Restful",
                description = "API Restful",
                version = "v1.0.0",
                termsOfService = "https://github.com/viniciusdsandrade/best-practices-in-java-and-spring/tree/main/2-melhorando-codigo-de-uma-api-com-refatoracao-solid-e-design-patterns",
                contact = @Contact(
                        name = "Vinícius dos Santos Andrade",
                        email = "vinicius_andrade2010@hotmail.com",
                        url = "https://www.linkedin.com/in/viniciusdsandrade"
                )
        )
)
public class OpenApiConfig {
}
