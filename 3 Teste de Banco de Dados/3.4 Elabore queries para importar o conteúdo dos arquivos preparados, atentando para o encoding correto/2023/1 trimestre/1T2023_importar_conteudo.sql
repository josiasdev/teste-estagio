-- Importar Dados do Arquivo CSV

COPY primeiroTrimestre2023 FROM '/media/HD_500GB/Projetos/teste/3 Teste de Banco de Dados/3.4 Elabore queries para importar o conteúdo dos arquivos preparados, atentando para o encoding correto/2023/1 trimestre/1T2023.csv' WITH (FORMAT CSV, DELIMITER ';', ENCODING 'UTF-8', HEADER TRUE);
