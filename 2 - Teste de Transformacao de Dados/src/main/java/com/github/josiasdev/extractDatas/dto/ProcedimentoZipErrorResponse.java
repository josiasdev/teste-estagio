package com.github.josiasdev.extractDatas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Resposta dos erros de procedimento do arquivo zip")
@Getter
@Setter
public class ProcedimentoZipErrorResponse {
    @Schema(description = "Mensagem de erro (se houver)", example = "Não foi possivel encontrar o arquivo Teste_{Josias}.zip")
    private String erro;
}
