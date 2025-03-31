package com.github.josiasdev.model;

import com.aspose.pdf.*;

public class ProcedimentoService {

    public boolean extrairDadosPdf(String filename) {

        Document pdfDocument = new Document(filename);
        TableAbsorber absorber = new TableAbsorber();
        for (Page page : pdfDocument.getPages()) {
            absorber.visit(page);

            for (AbsorbedTable table : absorber.getTableList()) {
                for (AbsorbedRow row : table.getRowList()) {
                    for (AbsorbedCell cell : row.getCellList()) {
                        for (TextFragment fragment : cell.getTextFragments()) {
                            StringBuilder sb = new StringBuilder();
                            for (TextSegment seg : fragment.getSegments()) {
                                sb.append(seg.getText());
                                System.out.print(sb.toString() + "|");
                            }
                        }
                    }
                }
            }
        }
        System.out.println();
        return true;
    }
}