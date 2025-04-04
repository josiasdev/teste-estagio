package com.github.josiasdev.extractDatas.controller;

import com.github.josiasdev.extractDatas.dto.ProcedimentoErrorResponse;
import com.github.josiasdev.extractDatas.dto.ProcedimentoResponse;
import com.github.josiasdev.extractDatas.dto.ProcedimentoZipErrorResponse;
import com.github.josiasdev.extractDatas.dto.ProcedimentoZipResponse;
import com.github.josiasdev.extractDatas.service.ProcedimentoService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jdk.jfr.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoController {
    private final ProcedimentoService procedimentoService;

    @Autowired
    public ProcedimentoController(ProcedimentoService procedimentoService){
        this.procedimentoService = procedimentoService;
    }
    @PostMapping("/extrair")
    @Operation(summary = "Extrair dados do Anexo I.pdf e gera um arquivo zip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processamento concluído com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcedimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao processar arquivo",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcedimentoErrorResponse.class)))
    })
    public ResponseEntity<ProcedimentoResponse> processar(){
        ProcedimentoResponse response = new ProcedimentoResponse();
        ProcedimentoErrorResponse errorResponse = new ProcedimentoErrorResponse();
        boolean extraido = procedimentoService.extrairTextoDoPDF("Anexo I.pdf");

        if (extraido) {
            response.setMensagem("Dados extraídos com sucesso!");
            procedimentoService.compactarParaZip("Teste_{Josias}.zip", "rol_procedimento.csv");
            response.setZip("Teste_{Josias}.zip");
            return ResponseEntity.ok(response);
        } else {
            errorResponse.setErro("Não foi possível encontrar o Anexo I.pdf");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/zip")
    @Operation(
            summary = "Faz o download do arquivo ZIP do arquivo de planilha em csv",
            description = "Retorna o arquivo ZIP contendo o arquivo de planilha o rol_procedimento.csv"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Download realizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcedimentoZipResponse.class))),
            @ApiResponse(responseCode = "404", description = "Arquivo ZIP não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcedimentoZipErrorResponse.class))),
    })
    public ResponseEntity<?> downloadZip() {
        File zipFile = new File("Teste_{Josias}.zip");

        if (!zipFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
                    .contentLength(zipFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao ler o arquivo ZIP.");
        }
    }
}
