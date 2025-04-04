from flask import Flask, request, jsonify
from flask_cors import CORS
import csv
from datetime import datetime

app = Flask(__name__)
CORS(app)

def load_operadoras():
    operadoras = []
    with open('relatorio_cadop.csv', 'r', encoding='UTF-8') as file:
        reader = csv.DictReader(file, delimiter=';')
        for row in reader:
            operadora = {
                'Registro_ANS': row.get('Registro_ANS', '').strip(),
                'CNPJ': row.get('CNPJ', '').strip(),
                'Razao_Social': row.get('Razao_Social', '').strip(),
                'Nome_Fantasia': row.get('Nome_Fantasia', '').strip(),
                'Modalidade': row.get('Modalidade', '').strip(),
                'UF': row.get('UF', '').strip(),
                'Telefone': row.get('Telefone', '').strip(),
                'Representante': row.get('Representante', '').strip(),
                'Cargo_Representante': row.get('Cargo_Representante', '').strip(),
                'Data_Registro_ANS': row.get('Data_Registro_ANS', '').strip()
            }
            operadoras.append(operadora)
    return operadoras


# Rota para buscar operadoras por nome
@app.route('/buscar-operadora', methods=['GET'])
def buscar_operadora():
    termo = request.args.get('termo', '').strip()
    filtro = request.args.get('filtro', 'Nome_Fantasia').strip()

    if not termo:
        return jsonify({'error': 'Por favor, forneça um termo para buscar'}), 400

    operadoras = load_operadoras()
    if filtro not in ['Razao_Social', 'Nome_Fantasia', 'Modalidade', 'UF']:
        return jsonify({'error': 'Filtro inválido'}), 400
    

    resultados = [operadora for operadora in operadoras if termo.upper() in operadora[filtro].upper()]
    

    return jsonify(resultados)

if __name__ == '__main__':
    app.run(debug=True)
