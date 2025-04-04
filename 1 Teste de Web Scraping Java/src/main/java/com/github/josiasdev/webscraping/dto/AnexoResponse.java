package com.github.josiasdev.webscraping.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Resposta contendo o status dos downloads e da criação do ZIP.")
@Getter
@Setter
public class AnexoResponse {
    @Schema(description = "Status do download do Anexo I", example = "Baixado")
    private String anexoI;

    @Schema(description = "Status do download do Anexo II", example = "Baixado")
    private String anexoII;

    @Schema(description = "Status da criação do arquivo ZIP", example = "ZIP criado com sucesso")
    private String zip;
}
