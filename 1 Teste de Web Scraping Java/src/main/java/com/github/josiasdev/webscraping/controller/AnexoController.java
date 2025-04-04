package com.github.josiasdev.webscraping.controller;
import com.github.josiasdev.webscraping.dto.AnexoErrorResponse;
import com.github.josiasdev.webscraping.service.AnexoService;
import com.github.josiasdev.webscraping.dto.AnexoResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/anexos")
public class AnexoController {
    private final AnexoService anexoService;

    @Autowired
    public AnexoController(AnexoService anexoService) {
        this.anexoService = anexoService;
    }


    @GetMapping("/download")
    @Operation(
            summary = "Baixar os arquivos informados e cria um ZIP",
            description = "Realiza o scraping da página informada via parâmetro, procura os links cujos textos sejam iguais aos nomes fornecidos e baixa os arquivos com esses nomes. Em seguida, cria um arquivo ZIP contendo os dois arquivos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processamento realizado com sucesso",
                    content= @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnexoResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao conectar à página",
                    content= @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnexoErrorResponse.class)))
    })
    public ResponseEntity<AnexoResponse> processar(
            @Parameter(description = "URL da página que será processada", example = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos")
            @RequestParam String url,

            @Parameter(description = "Nome do primeiro arquivo a ser baixado (deve corresponder ao texto do link) e salvo com extensão, ex.: 'Anexo I.pdf'", example = "Anexo I.pdf")
            @RequestParam String anexoI,

            @Parameter(description = "Nome do segundo arquivo a ser baixado (deve corresponder ao texto do link) e salvo com extensão, ex.: 'Anexo II.pdf'", example = "Anexo II.pdf")
            @RequestParam String anexoII
    ){
        AnexoResponse response = new AnexoResponse();

        if (url.isBlank() || anexoI.isBlank() || anexoII.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            String anexo1Url = "";
            String anexo2Url = "";
            for (Element link : links) {
                String text = link.text().trim();
                if (text.equals("Anexo I.")) {
                    anexo1Url = link.attr("abs:href");
                }
                if (text.equals("Anexo II.")) {
                    anexo2Url = link.attr("abs:href");
                }
            }

            boolean sucessoAnexoI = anexoService.downloadAnexo(anexo1Url, "Anexo I.pdf");
            boolean sucessoAnexoII = anexoService.downloadAnexo(anexo2Url, "Anexo II.pdf");

            response.setAnexoI(sucessoAnexoI ? "Baixado" : "Falha");
            response.setAnexoII(sucessoAnexoII ? "Baixado" : "Falha");



            if (sucessoAnexoI || sucessoAnexoII) {
                boolean zipCriado = anexoService.criarArquivoZip("anexos.zip", "Anexo I.pdf", "Anexo II.pdf");
                response.setZip(zipCriado ? "ZIP criado com sucesso" : "Falha ao criar o ZIP");
            }
            else{
                response.setZip("Nenhum anexo foi baixado. ZIP não foi criado.");
            }

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/zip")
    @Operation(
            summary = "Faz o download do arquivo ZIP dos anexos",
            description = "Retorna o arquivo ZIP contendo os anexos I e II se ele já tiver sido criado anteriormente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Download realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Arquivo ZIP não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao ler o arquivo ZIP")
    })
    public ResponseEntity<?> downloadZip() {
        File zipFile = new File("anexos.zip");

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