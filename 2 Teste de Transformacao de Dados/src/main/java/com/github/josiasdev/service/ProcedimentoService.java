package com.github.josiasdev.service;

import com.aspose.pdf.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.aspose.zip.Archive;
import com.aspose.zip.ArchiveEntrySettings;

public class ProcedimentoService {

    public boolean extrairDadosPdf(String filename) {
        try {
            Document pdfDocument = new Document(filename);
            TableAbsorber absorber = new TableAbsorber();
            StringBuilder csvContent = new StringBuilder();
            for (Page page : pdfDocument.getPages()){
                absorber.visit(page);

                    for (AbsorbedTable table : absorber.getTableList()) {
                        List<Integer> targetColumns = new ArrayList<>();
                        if (!table.getRowList().isEmpty()) {
                            AbsorbedRow headerRow = table.getRowList().get(0);
                            int columnIndex = 0;
                            for (AbsorbedCell cell : headerRow.getCellList()) {
                                StringBuilder headerTextBuilder = new StringBuilder();
                                for (TextFragment fragment : cell.getTextFragments()) {
                                    for (TextSegment seg : fragment.getSegments()) {
                                        headerTextBuilder.append(seg.getText().trim()).append(" ");
                                    }
                                }
                                String headerText = headerTextBuilder.toString().trim();
                                if (headerText.equalsIgnoreCase("OD") || headerText.equalsIgnoreCase("AMB")) {
                                    targetColumns.add(columnIndex);
                                }
                                columnIndex++;
                            }
                        }

                        for (AbsorbedRow row : table.getRowList()) {
                            StringBuilder csvRow = new StringBuilder();
                            int columnIndex = 0;
                            for (AbsorbedCell cell : row.getCellList()) {
                                StringBuilder sb = new StringBuilder();
                                for (TextFragment fragment : cell.getTextFragments()) {
                                    for (TextSegment seg : fragment.getSegments()) {
                                       String text = seg.getText().trim();
                                        if (targetColumns.contains(columnIndex)) {
                                            text = text.replace("OD", "Seg. Odontológica")
                                                    .replace("AMB", "Seg. Ambulatorial");
                                        }
                                        sb.append(text).append(" ");
                                    }
                                }
                                String cellText = sb.toString().trim().replace("\"", "'");
                                csvRow.append('"').append(cellText).append('"').append(",");
                                columnIndex++;
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

    public void compactarZip(){
        try(FileOutputStream zipFile = new FileOutputStream("teste_{josias}.zip")){
            try (FileInputStream source1 = new FileInputStream("rol_procedimento.csv")){
                try (Archive archive = new Archive(new ArchiveEntrySettings())){
                    archive.createEntry("rol_procedimento.csv", source1);
                    archive.save(zipFile);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}