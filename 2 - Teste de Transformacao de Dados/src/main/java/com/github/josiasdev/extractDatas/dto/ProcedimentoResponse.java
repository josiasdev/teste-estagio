package com.github.josiasdev.extractDatas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Resposta do processamento dos procedimentos")

@Getter
@Setter
public class ProcedimentoResponse {
    @Schema(description = "Mensagem de sucesso", example = "Dados extraídos com sucesso!")
    private String mensagem;

    @Schema(description = "Nome do arquivo ZIP gerado", example = "Teste_{Josias}.zip")
    private String zip;
}
