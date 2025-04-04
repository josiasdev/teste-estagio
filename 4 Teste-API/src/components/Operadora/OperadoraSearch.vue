<script>
import '../../assets/OperadoraSearch.css'
export default {
  data() {
    return {
      termo: "",
      filtro: "Razao_Social",
      operadoras: [],
    };
  },
  methods: {
    buscarOperadora() {
      fetch(`http://127.0.0.1:5000/buscar-operadora?termo=${this.termo}&filtro=${this.filtro}`)
        .then((response) => response.json())
        .then((data) => {
          this.operadoras = data;
        })
        .catch((error) => console.error("Erro ao buscar operadoras:", error));
    },
    formatKey(key) {
      return key.replace(/_/g, " ").toUpperCase();
    },
  },
};
</script>

<template>
   <div class="container">
    <h1>Buscar Operadora</h1>
    
    <div class="filters">
      <input type="text" v-model="termo" placeholder="Digite o termo de busca" />
      <select v-model="filtro">
        <option value="Razao_Social">Razão Social</option>
        <option value="Nome_Fantasia">Nome Fantasia</option>
        <option value="Modalidade">Modalidade</option>
        <option value="UF">UF</option>
      </select>
      <button @click="buscarOperadora">Buscar</button>
    </div>

    <div v-if="operadoras.length > 0" class="result">
      <div v-if="filtro === 'Razao_Social'">
        <div class="operadora" v-for="operadora in operadoras" :key="operadora.Razao_Social">
        <h2>Resultados para Razão Social: {{ operadora.Razao_Social }}</h2>
          <p><strong>Nome Fantasia:</strong> {{ operadora.Nome_Fantasia }}</p>
          <p><strong>Modalidade:</strong> {{ operadora.Modalidade }}</p>
          <p><strong>UF:</strong> {{ operadora.UF }}</p>
        </div>
      </div>

      <div v-if="filtro === 'Nome_Fantasia'">
        <div class="operadora" v-for="operadora in operadoras" :key="operadora.Nome_Fantasia">
        <h2>Resultados para Nome Fantasia: {{ operadora.Nome_Fantasia }}</h2>
          <p><strong>Razão Social:</strong> {{ operadora.Razao_Social }}</p>
          <p><strong>Modalidade:</strong> {{ operadora.Modalidade }}</p>
          <p><strong>UF:</strong> {{ operadora.UF }}</p>
        </div>
      </div>

      <div v-if="filtro === 'Modalidade'">
        <div class="operadora" v-for="operadora in operadoras" :key="operadora.Modalidade">
        <h2>Resultados para Modalidade: {{ operadora.Modalidade }}</h2>
          <p><strong>Razão Social:</strong> {{ operadora.Razao_Social }}</p>
          <p><strong>Nome Fantasia:</strong> {{ operadora.Nome_Fantasia }}</p>
          <p><strong>UF:</strong> {{ operadora.UF }}</p>
        </div>
      </div>

      <div v-if="filtro === 'UF'">
        <div class="operadora" v-for="operadora in operadoras" :key="operadora.UF">
        <h2>Resultados para Estado {{ operadora.UF }}</h2>
          <p>Razão Social: {{ operadora.Razao_Social }}</p>
          <p>Nome Fantasia: {{ operadora.Nome_Fantasia }}</p>
          <p>Modalidade: {{ operadora.Modalidade }}</p>
        </div>
      </div>
      <p v-else class="no-result">Nenhum resultado encontrado.</p>
    </div>
    </div>
</template>