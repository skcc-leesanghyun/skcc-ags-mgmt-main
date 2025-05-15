package com.skcc.ags.knowledge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.skcc.ags.knowledge.mapper")
@OpenAPIDefinition(
    info = @Info(
        title = "AGS Knowledge Management API",
        version = "1.0",
        description = "API documentation for AGS Knowledge Management Service"
    ),
    servers = {
        @Server(url = "/api/knowledge", description = "Knowledge Service API")
    }
)
public class KnowledgeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeServiceApplication.class, args);
    }
} 