package com.github.josiasdev.extractDatas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "ExtractDatas", version = "1", description = "API desenvolvida para o teste de transformação de dados."))
@SpringBootApplication
public class ExtractDatasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtractDatasApplication.class, args);
	}

}
