package com.github.josiasdev.webScraping.controller;
import com.github.josiasdev.webScraping.service.AnexoService;
import com.github.josiasdev.webScraping.view.ConsoleView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AnexoController {
    private final AnexoService anexoService;
    private final ConsoleView view;

    public AnexoController(AnexoService anexoService, ConsoleView view){
        this.anexoService = anexoService;
        this.view = view;
    }

    public void processar(){
        try{
            String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
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

            if (sucessoAnexoI){
                view.exibirMensagem("Download concluído: Anexo I.pdf");
            }
            else{
                view.exibirErro("Erro ao baixar Anexo I.pdf");
            }

            if (sucessoAnexoII){
                view.exibirMensagem("Download concluido: Anexo II.pdf");
            }
            else{
                view.exibirErro("Erro ao baixar Anexo II.pdf");
            }
            if (sucessoAnexoI || sucessoAnexoII){
                boolean zipCriado = anexoService.criarArquivoZip("anexos.zip", "Anexo I.pdf", "Anexo II.pdf");
                if (zipCriado){
                    view.exibirMensagem("Arquivo ZIP criado com sucesso.");
                }
                else{
                    view.exibirErro("Erro ao criar o arquivo ZIP.");
                }
            }

        } catch (IOException e){
            view.exibirErro("Erro ao conectar a página: " + e.getMessage());
        }
    }


}