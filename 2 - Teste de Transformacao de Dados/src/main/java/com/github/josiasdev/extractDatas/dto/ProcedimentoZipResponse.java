package com.github.josiasdev.extractDatas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Resposta do processamento do arquivo zip")
@Getter
@Setter
public class ProcedimentoZipResponse {
    @Schema(description = "Mensagem de sucesso", example = "Download realizado com sucesso")
    private String mensagem;
}
