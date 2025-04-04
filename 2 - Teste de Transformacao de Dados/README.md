# 📊 Transformação de Dados com Java

Este projeto é um **Teste de Transformação de Dados** utilizando **Java 24**, **Spring Boot 3.4.4**, **PDFBox**, **Tabula** e **Swagger UI**. A aplicação realiza a extração de dados tabulares de um arquivo PDF, estrutura os dados em formato CSV, substitui abreviações por descrições completas e compacta o resultado em um arquivo .zip.


## 🚀 Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java-24-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Swagger UI](https://img.shields.io/badge/Swagger-UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![JSoup](https://img.shields.io/badge/JSoup-HTML%20Parser-blue?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18.38-red?style=for-the-badge&logo=java&logoColor=white)
![PDFBox](https://img.shields.io/badge/PDFBox-2.0.29-lightgrey?style=for-the-badge)
![Tabula](https://img.shields.io/badge/Tabula-1.0.5-lightgrey?style=for-the-badge)
![SLF4J](https://img.shields.io/badge/SLF4J-2.0.9-blue?style=for-the-badge&logo=log4j)


## 🧱 Estrutura do Projeto

````
📦 2 Teste de Transformacao de Dados
├── 📁 src
│   └── 📁 main
│       └── 📁 java
│           └── 📁 com
│               └── 📁 github
│                   └── 📁 josiasdev
│                       └── 📁 extractDatas
│                           ├── 📁 controller
│                           │   └── 📄 ProcedimentoController.java
│                           ├── 📁 dto
│                           │   ├── 📄 ProcedimentoErrorResponse.java
│                           │   ├── 📄 ProcedimentoResponse.java
│                           │   └── 📄 ProcedimentoZipErrorResponse.java
│                           │    ├── 📄 ProcedimentoZipResponse.java
│                           └── 📁 service
│                               └── 📄 ProcedimentoService.java
├── 📁 resources
│   └── 📄 application.properties
│   └── 📄 logback-spring.xml
├── 📄 pom.xml
└── 📄 README.md
````

- `ProcedimentoController`: expõe os endpoints REST para realizar a transformação de dados.
- `ProcedimentoService`: camada de serviço onde está implementada a lógica de transformação de dados usando pdf box e tabula.
- `ProcedimentoResponse`: DTO para encapsular a resposta da API com os dados baixados.
- `ProcedimentoErrorResponse`: DTO para retorno de erros estruturados.
- `ProcedimentoZipResponse`: DTO para encapsular a resposta da API com os dados baixados do zip.
- `ProcedimentoZipErrorResponse`: DTO para retorno de erros estruturados.

## 📦 Como Rodar o projeto

1. Clone o Repositoŕio

```bash
git clone https://github.com/josiasdev/teste-estagio/
cd 2\ -\ Teste\ de\ Transformacao\ de\ Dados/
```

2. Compile e rode com Maven
```bash
./mvnw spring-boot:run
```

3. Acesse para visualiar e testar a API
```bash
http://localhost:8080/swagger-ui/index.html
```

# Rotas da API

### 📤 `POST /procedimentos/extrair`
Extrai os dados do arquivo **`Anexo I.pdf`**, transforma os dados em um CSV chamado `rol_procedimento.csv`, substitui as abreviações por descrições completas e compacta o CSV em um arquivo ZIP chamado `Teste_{Josias}.zip`.

### Resposta de sucesso (200 OK):

```json
{
  "mensagem": "Dados extraídos com sucesso!",
  "zip": "Teste_{Josias}.zip"
}
```

### Erros possíveis:

400 Bad Request – Arquivo PDF não encontrado ou erro no processamento.
```json
{
  "erro": "Não foi possível encontrar o Anexo I.pdf"
}
```

### 📤 `POST /procedimentos/zip`
Realiza o download do arquivo ZIP gerado após a extração e transformação dos dados.

### Respostas possiveis:
200 OK – Retorna o arquivo `.zip` com o CSV incluido.<br>
404 Not Found – O arquivo ZIP ainda não foi gerado ou não está disponível.<br>
500 Internal Server Error – Ocorreu um erro ao tentar acessar o arquivo ZIP.