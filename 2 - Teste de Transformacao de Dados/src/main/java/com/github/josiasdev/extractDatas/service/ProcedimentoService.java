package com.github.josiasdev.extractDatas.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ProcedimentoService {

    private static final int TAMANHO_BUFFER = 4096;
    private static final String CSV_FILENAME = "rol_procedimento.csv";
    private static final Logger logger = LoggerFactory.getLogger(ProcedimentoService.class);

    public boolean extrairTextoDoPDF(String filename) {
        // o load do PDDocument só funciona na versão 2.0.29 nas novas versões foi removido.
        try (PDDocument document = PDDocument.load(new File(filename))) {
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

            List<Page> pages = new ArrayList<>();
            extractor.extract().forEachRemaining(pages::add);

            StringBuilder csvContent = new StringBuilder();
            for (Page page : pages) {
                List<Table> tables = sea.extract(page);
                for (Table table : tables) {
                    for (List<RectangularTextContainer> row : table.getRows()) {
                        StringBuilder csvRow = new StringBuilder();
                        for (RectangularTextContainer cell : row) {
                            String cellText = formatarCelula(cell.getText());
                            csvRow.append('"').append(cellText).append('"').append(",");
                        }
                        if (csvRow.length() > 0) {
                            csvContent.append(csvRow.substring(0, csvRow.length() - 1)).append("\n");
                        }
                    }
                }
            }

            if (csvContent.length() > 0) {
                salvarCsv(CSV_FILENAME, csvContent.toString());
                return true;
            }

            return false;

        } catch (IOException e) {
            logger.error("Erro ao extrair texto do PDF", e);
            return false;
        }
    }

    private String formatarCelula(String texto) {
        texto = texto.trim().replace("\"", "''");
        return switch (texto) {
            case "OD" -> "Seg. Odontológica";
            case "AMB" -> "Seg. Ambulatorial";
            default -> texto;
        };
    }

    private void salvarCsv(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
            logger.info("CSV salvo com sucesso: {}", filename);
        } catch (IOException e) {
            logger.error("Erro ao salvar CSV", e);
        }
    }

    public boolean compactarParaZip(String nomeZip, String nomeArquivo) {
        File inputFile = new File(nomeArquivo);
        if (!inputFile.exists()) {
            logger.warn("Arquivo não encontrado para compactar: {}", nomeArquivo);
            return false;
        }

        try (
                FileOutputStream fos = new FileOutputStream(nomeZip);
                ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
                FileInputStream fis = new FileInputStream(inputFile);
                BufferedInputStream bis = new BufferedInputStream(fis, TAMANHO_BUFFER)
        ) {
            ZipEntry entry = new ZipEntry(inputFile.getName());
            zos.putNextEntry(entry);

            byte[] buffer = new byte[TAMANHO_BUFFER];
            int count;
            while ((count = bis.read(buffer)) != -1) {
                zos.write(buffer, 0, count);
            }

            logger.info("ZIP criado com sucesso: {}", nomeZip);
            return true;

        } catch (IOException e) {
            logger.error("Erro ao criar ZIP", e);
            return false;
        }
    }
}
