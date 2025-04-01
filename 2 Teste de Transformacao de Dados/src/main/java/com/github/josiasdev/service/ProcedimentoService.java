package com.github.josiasdev.service;

import com.aspose.pdf.*;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProcedimentoService {
    static final int TAMANHO_BUFFER = 4096;

    public boolean extrairDadosPdf(String filename) {
        try {
            Document pdfDocument = new Document(filename);
            TableAbsorber absorber = new TableAbsorber();
            StringBuilder csvContent = new StringBuilder();
            for (Page page : pdfDocument.getPages()){
                absorber.visit(page);

                    for (AbsorbedTable table : absorber.getTableList()) {
                        for (AbsorbedRow row : table.getRowList()) {
                            StringBuilder csvRow = new StringBuilder();
                            for (AbsorbedCell cell : row.getCellList()) {
                                StringBuilder sb = new StringBuilder();
                                TextFragmentCollection textFragmentCollection = cell.getTextFragments();
                                for (TextFragment fragment : textFragmentCollection) {
                                    for (TextSegment seg : fragment.getSegments()) {
                                        sb.append(seg.getText().trim()).append(" ");
                                    }
                                }
                                String cellText = sb.toString().trim().replace("\"", "'");
                                csvRow.append('"').append(cellText).append('"').append(",");
                            }
                            if (csvRow.length() > 0) {
                                csvContent.append(csvRow.substring(0, csvRow.length() - 1)).append("\n"); // Remove última vírgula
                            }
                        }
                    }
            }
            if (csvContent.length() > 0) {
                salvarCsv("rol_procedimento.csv", csvContent.toString());
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
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
