package com.github.josiasdev.extractDatas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Resposta dos erros de procedimento")

@Getter
@Setter
public class ProcedimentoErrorResponse {
    @Schema(description = "Mensagem de erro (se houver)", example = "Não foi possivel encontrar o arquivo Anexo I.pdf")
    private String erro;
}
