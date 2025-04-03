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
