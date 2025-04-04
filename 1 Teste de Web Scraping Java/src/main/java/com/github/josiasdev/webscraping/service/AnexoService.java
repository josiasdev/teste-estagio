package com.github.josiasdev.webscraping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AnexoService {

    private static final Logger logger = LoggerFactory.getLogger(AnexoService.class);

    public boolean downloadAnexo(String pdfUrl, String fileName) {
        Path filePath = Paths.get(fileName);
        try (InputStream in = new URL(pdfUrl).openStream();
             OutputStream out = Files.newOutputStream(filePath, StandardOpenOption.CREATE)){

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            logger.info("Download concluido: {}", filePath);
            return true;
        } catch (IOException e) {
            logger.error("Erro ao baixar o anexo: {}", pdfUrl, e);
            return false;
        }
    }

    public boolean criarArquivoZip(String zipFileName, String... filesToArchive) {
        Path zipPath = Paths.get(zipFileName);
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            for (String fileName : filesToArchive) {
                Path filePath = Paths.get(fileName);
                if (!Files.exists(filePath)){
                    logger.warn("Arquivo não encontrado e será ignorado: {}", filePath);
                    continue;
                }

                try (InputStream fis = Files.newInputStream(filePath)) {
                    zos.putNextEntry(new ZipEntry(fileName));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                    logger.info("Arquivo adicionado ao ZIP: {}", fileName);
                }
            }
            logger.info("Arquivo ZIP criado com sucesso: {}", zipPath);
            return true;
        } catch (IOException e) {
            logger.error("Erro ao criar o arquivo ZIP", e);
            return false;
        }
    }
    public Path getZipFilePath(String zipFileName) {
        return Paths.get(zipFileName);
    }
}
