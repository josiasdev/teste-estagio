package com.github.josiasdev.webScraping.model;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AnexoService {

    public boolean downloadAnexo(String pdfUrl, String fileName) {
        try (InputStream in = new URL(pdfUrl).openStream();
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean criarArquivoZip(String zipFileName, String... filesToArchive) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            for (String fileName : filesToArchive) {
                File file = new File(fileName);
                if (!file.exists()) continue; // Ignora arquivos inexistentes

                try (FileInputStream fis = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
