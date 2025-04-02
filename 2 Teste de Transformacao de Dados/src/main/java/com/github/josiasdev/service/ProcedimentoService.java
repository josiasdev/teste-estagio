package com.github.josiasdev.service;

import org.apache.pdfbox.pdmodel.PDDocument;
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


public class ProcedimentoService{
    static final int TAMANHO_BUFFER = 4096;

    public boolean extrairTextoDoPDF(String filename) {
        try (PDDocument document = PDDocument.load(new File(filename))) {
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            List<Page> pages = new ArrayList<>();
            extractor.extract().forEachRemaining(pages::add);

            StringBuilder csvContent = new StringBuilder();
            for (Page page : pages) {
                List<Table> tables = sea.extract(page);

                for (Table table : tables) {
                    for (List<RectangularTextContainer> row : table.getRows()){
                        StringBuilder csvRow = new StringBuilder();
                        for (RectangularTextContainer cell : row){
                            String cellText = cell.getText().trim().replace("\"", "''");
                            if (cellText.equals("OD")) {
                                cellText = "Seg. Odontológica";
                            } else if (cellText.equals("AMB")) {
                                cellText = "Seg. Ambulatorial";
                            }
                            csvRow.append('"').append(cellText).append('"').append(",");
                        }
                        if (csvRow.length() > 0){
                            csvContent.append(csvRow.substring(0, csvRow.length() - 1)).append("\n");
                        }
                    }
                }
            }
            if (csvContent.length() > 0) {
                salvarCsv("rol_procedimento.csv", csvContent.toString());
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void salvarCsv(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compactarParaZip(String arqSaida, String arqEntrada){
        int cont;
        byte[] dados = new byte[TAMANHO_BUFFER];
        BufferedInputStream origem = null;
        FileInputStream streamEntrada = null;
        FileOutputStream destino = null;
        ZipOutputStream saida = null;
        ZipEntry entry = null;
        try{
            destino = new FileOutputStream(new File(arqSaida));
            saida = new ZipOutputStream(new BufferedOutputStream(destino));
            File file = new File(arqEntrada);
            streamEntrada = new FileInputStream(file);
            origem = new BufferedInputStream(streamEntrada, TAMANHO_BUFFER);
            entry = new ZipEntry(file.getName());
            saida.putNextEntry(entry);
            while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1){
                saida.write(dados, 0, cont);
            }
            origem.close();
            saida.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}