-- Importar Dados do Arquivo CSV

COPY primeiroTrimestre2024 FROM '/media/HD_500GB/Projetos/teste/3 Teste de Banco de Dados/3.4 Elabore queries para importar o conteúdo dos arquivos preparados, atentando para o encoding correto/2024/1 trimestre/1T2024.csv' WITH (FORMAT CSV, DELIMITER ';', ENCODING 'UTF-8', HEADER TRUE);
