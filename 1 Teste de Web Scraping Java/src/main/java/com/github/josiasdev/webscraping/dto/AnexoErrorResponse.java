package com.github.josiasdev.webscraping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnexoErrorResponse {
    @Schema(description = "Mensagem de erro (se houver)", example = "Erro ao conectar à página")
    private String erro;
}
