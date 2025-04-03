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
