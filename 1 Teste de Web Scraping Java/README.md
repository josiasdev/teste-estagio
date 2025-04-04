# 🕷️ Web Scraping com Spring Boot

Este projeto é uma aplicação simples de **Web Scraping** utilizando **Spring Boot 3.4.4**, **Java 24**, **JSoup** para a extração de dados da web e **Swagger UI** para a documentação interativa da API.

## 🚀 Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Swagger UI](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![JSoup](https://img.shields.io/badge/JSoup-1.19.1-blue?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18.38-red?style=for-the-badge&logo=java&logoColor=white)



## 🧱 Estrutura do Projeto

````
📦 1 Teste de Web Scraping Java
├── 📁 src
│   └── 📁 main
│       └── 📁 java
│           └── 📁 com
│               └── 📁 github
│                   └── 📁 josiasdev
│                       └── 📁 webscraping
│                           ├── 📁 controller
│                           │   └── 📄 AnexoController.java
│                           ├── 📁 dto
│                           │   ├── 📄 AnexoResponse.java
│                           │   └── 📄 AnexoErrorResponse.java
│                           └── 📁 service
│                               └── 📄 AnexoService.java
├── 📁 resources
│   └── 📄 application.properties
├── 📄 pom.xml
└── 📄 README.md
````


- `AnexoController`: expõe os endpoints REST para realizar o scraping.
- `AnexoService`: camada de serviço onde está implementada a lógica de scraping usando **JSoup**.
- `AnexoResponse`: DTO para encapsular a resposta da API com os dados extraídos.
- `AnexoErrorResponse`: DTO para retorno de erros estruturados.


## 📦 Como Rodar o projeto

1. Clone o Repositoŕio

```bash
git clone https://github.com/josiasdev/teste-estagio/
cd 1\ Teste\ de\ Web\ Scraping\ Java/
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

### 🔽 `GET /anexos/download`
  Realiza scraping da página informada, localiza dois links com nomes correspondentes aos parâmetros, baixa os arquivos e os compacta em um ZIP.

| Parâmetro | Tipo   | Obrigatório | Exemplo                                                                                                                    | Descrição                                                                                   |
|-----------|--------|-------------|----------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| `url`     | string | Sim         | `https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos`          | URL da página onde os anexos estão disponíveis.                                             |
| `anexoI`  | string | Sim         | `Anexo I.pdf`                                                                                                              | Nome do primeiro anexo conforme aparece no link da página. Deve incluir a extensão `.pdf`. |
| `anexoII` | string | Sim         | `Anexo II.pdf`                                                                                                             | Nome do segundo anexo conforme aparece no link da página. Deve incluir a extensão `.pdf`.  |

### Resposta de sucesso (200 OK):

```bash
{
  "anexoI": "Baixado",
  "anexoII": "Baixado",
  "zip": "ZIP criado com sucesso"
}
```

### Erros possíveis:

400 Bad Request – Parâmetros inválidos ou ausentes. <br>
500 Internal Server Error – Falha ao acessar a página ou realizar o scraping.



### 🔽 `GET /anexos/zip`
Faz o download do arquivo ZIP contendo os anexos baixados anteriormente.


### Respostas possiveis:
200 OK – Retorna o arquivo ZIP para download.<br>
404 Not Found – Arquivo ZIP não encontrado (não foi criado ainda).<br>
500 Internal Server Error – Erro ao tentar ler o arquivo ZIP.