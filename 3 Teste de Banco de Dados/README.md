# 🗄️ Teste de Banco de Dados com PostgreSQL

Este projeto envolve a estruturação de um banco de dados relacional para armazenar e analisar demonstrações contábeis de operadoras de plano de saúde, utilizando PostgreSQL 10+.

📌 Tarefas Realizadas

## 1 - Download dos arquivos (OK)<br>
## 2 - Criação da Estrutura do Banco de Dados (OK)

Exemplo de criação da tabela para armazenar os dados de todos os trimestres de 2023 e de 2024:
```sql
CREATE table primeiroTrimestre2023 (
        data_ DATE,
        reg_ans VARCHAR(10),
        cd_conta_contabil VARCHAR(20),
        descricao VARCHAR(255),
        vl_saldo_inicial VARCHAR(20),
        Vl_saldo_final VARCHAR(20)
);
```
Exemplo de criação de tabela para armazenar os dados das operadoras:
```sql
CREATE TABLE relatorio_cadop (
    registro_ans VARCHAR(50),
    cnpj VARCHAR(14),
    razao_social VARCHAR(255),
    nome_fantasia VARCHAR(255),
    modalidade VARCHAR(50),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    cep VARCHAR(8),
    ddd VARCHAR(2),
    telefone VARCHAR(20),
    fax VARCHAR(20),
    endereco_eletronico VARCHAR(255),
    representante VARCHAR(255),
    cargo_representante VARCHAR(100),
    regiao_comercializacao VARCHAR(255),
    data_registro_ans DATE
);
```

## 3 - Importação dos Dados (OK) <br>

Exemplo de importação de conteúdo do arquivo csv.
```sql
COPY primeiroTrimestre2023 FROM '/media/HD_500GB/Projetos/teste/3 Teste de Banco de Dados/3.4 Elabore queries para importar o conteúdo dos arquivos preparados, atentando para o encoding correto/2023/1 trimestre/1T2023.csv' WITH (FORMAT CSV, DELIMITER ';', ENCODING 'UTF-8', HEADER TRUE);
```
# 4 - Consultas Analiicas (OK)

## 4.1 - Quais as 10 operadoras com maiores despesas em "EVENTOS/ SINISTROS CONHECIDOS OU
AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR" no último trimestre?

```sql
-- Aqui criei uma nova tabela para guardar as despesas do 4 trimestre de 2024 acredito que fique mais organizado

CREATE TABLE despesas4trimestre2024 (
	operadora VARCHAR(255),
    descricao_evento VARCHAR(255),
    total_despesas DECIMAL(18, 2)
);

-- Aqui estou inserindo os dados na tabela que eu criei procurando por "EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR" a operadora equivale o reg_ans, a descricao_evento 
-- equivale a descrição onde tem o nome das operadoras, e as total_despesas equivale o valor total das despesas que tem o vl_saldo_inicial e vl_saldo_final

INSERT INTO despesas4trimestre2024 (operadora, descricao_evento, total_despesas)
SELECT 
	reg_ans AS operadora,
    descricao,
	SUM(CAST(REPLACE(vl_saldo_inicial, ',', '.') AS DECIMAL(18, 2)) +         CAST(REPLACE(vl_saldo_final, ',', '.') AS DECIMAL(18, 2))) AS total_despesas
FROM 
    quartotrimestre2024
WHERE 
    descricao LIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
    AND data_ = '2024-10-01' 
GROUP BY 
    reg_ans, descricao
ORDER BY 
    total_despesas DESC
LIMIT 10;


-- Aqui estou verificando se os dados foram para lá corretamente

SELECT * FROM despesas4trimestre2024;
```

## 4.2 - Quais as 10 operadoras com maiores despesas nessa categoria no último ano?
```sql
CREATE TABLE top_operadoras_2024 (
    operadora VARCHAR(255),
    descricao_evento VARCHAR(255),
    total_despesas DECIMAL(18, 2)
);

-- Aqui estou inserindo os dados na tabela que eu criei procurando por "EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR" a operadora equivale o reg_ans, a descricao_evento equivale a descrição onde tem o nome das operadoras, e as total_despesas equivale o valor total das despesas que tem o vl_saldo_inicial e vl_saldo_final

INSERT INTO top_operadoras_2024 (operadora, descricao_evento, total_despesas)
SELECT operadora, descricao_evento, SUM(total_despesas)
FROM (
    SELECT operadora, descricao_evento, total_despesas FROM despesas1trimestre2024
    UNION ALL
    SELECT operadora, descricao_evento, total_despesas FROM despesas2trimestre2024
    UNION ALL
    SELECT operadora, descricao_evento, total_despesas FROM despesas3trimestre2024
    UNION ALL
    SELECT operadora, descricao_evento, total_despesas FROM despesas4trimestre2024
)
GROUP BY operadora, descricao_evento
ORDER BY SUM(total_despesas) DESC
LIMIT 10;

-- Aqui estou verificando se os dados foram para lá corretamente
SELECT * FROM top_operadoras_2024;
```