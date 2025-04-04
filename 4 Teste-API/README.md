# Teste de API com Vue.js e Python

# 🚀 Tecnologias Utilizadas

[![Vue.js](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D)](https://vuejs.org/)
[![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)
[![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://www.python.org/)
[![Flask](https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white)](https://flask.palletsprojects.com/)
[![Flask-CORS](https://img.shields.io/badge/Flask--CORS-000000?style=for-the-badge&logo=flask&logoColor=white)](https://flask-cors.readthedocs.io/)

## 🧱 Estrutura do Projeto

````
📦 4 Teste-API
├── 📁 src
│   ├── 📁 assets
│   │   └── 📄 OperadoraSearch.css
│   ├── 📁 components
│   │   └── 📁 Operadora
│   │       └── 📄 OperadoraSearch.vue
│   ├── 📄 App.vue
│   └── 📄 main.js
├── 📄 package.json
├── 📄 vite.config.js
└── 📄 README.md
````

- `OperadoraSearch.vue`: Componente Vue responsável por buscar operadoras de plano de saúde via API Flask e exibir os resultados.
- `OperadoraSearch.css`: Estilos do componente `OperadoraSearch.vue`, garantindo uma interface escura e moderna.
- `App.vue`: Componente raiz da aplicação Vue.js, onde os componentes são renderizados.
- `main.js`: Arquivo de entrada da aplicação, onde o Vue.js é inicializado.

## Rodar o FrontEnd
1. Clone o Repositório
```bash
git clone https://github.com/josiasdev/teste-estagio/
cd 4\ Teste-API/
```

2. Instale as Dependências
```bash
npm install
```

3. Rodar o Servidor de Desenvolvimento
```bash
npm run dev
```

4. Abrir o link no navegador
```bash
http://localhost:5173/
```

## Rodar o BackEnd

1. Instale o Flask e o Flask-cors:
```bash
pip install flask flask-cors
```

2. Execute o arquivo Python:
No Windows
```bash
python servidor.py
```
No Linux
```bash
python3 servidor.py
```

3. Servidor estara disponivel em
```bash
http://127.0.0.1:5000
```

Exemplo de Requisição:
```bash
GET /buscar-operadora?termo=unimed&filtro=Nome_Fantasia
```