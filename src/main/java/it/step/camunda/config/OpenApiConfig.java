package it.step.camunda.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    private String devUrl = "http://localhost:8080";

    private String prodUrl ="http://localhost:8080";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");



        Info info = new Info()
                .title("Camunda Example API")
                .version("1.0")
                .description("This API show hou to use a BPMN process").termsOfService("");

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }

}