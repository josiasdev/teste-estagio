package com.github.josiasdev.service;

import com.aspose.pdf.*;

public class ProcedimentoService {

    public boolean extrairDadosPdf(String filename) {
        try {
            Document pdfDocument = new Document(filename);
            TableAbsorber absorber = new TableAbsorber();
            for (Page page : pdfDocument.getPages()) {
                absorber.visit(page);

                for (AbsorbedTable table : absorber.getTableList()) {
                    for (AbsorbedRow row : table.getRowList()) {
                        for (AbsorbedCell cell : row.getCellList()) {
                            StringBuilder sb = new StringBuilder();
                            for (TextFragment fragment : cell.getTextFragments()) {

                                for (TextSegment seg : fragment.getSegments()) {
                                    sb.append(seg.getText());
                                }
                            }
                            System.out.print(sb.toString() + " | "); // Separador de colunas
                        }
                        System.out.println();
                    }
                    System.out.println("----------------------------------------------------");
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}