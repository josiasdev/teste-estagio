package com.github.josiasdev.controller;

import com.github.josiasdev.service.ProcedimentoService;
import com.github.josiasdev.view.ConsoleView;

public class ProcedimentoController {
    private final ProcedimentoService procedimentoService;
    private final ConsoleView view;

    public ProcedimentoController(ProcedimentoService procedimentoService, ConsoleView view){
        this.procedimentoService = procedimentoService;
        this.view = view;
    }
    public void processar(){
        if(procedimentoService.extrairTextoDoPDF("Anexo I.pdf")){
            view.exibirMensagem("Dados extraidos com sucesso!");
            procedimentoService.compactarParaZip("Teste_{Josias}.zip", "rol_procedimento.csv");
            view.exibirMensagem("Arquivo compactado com sucesso!");
        }
        else{
            view.exibirErro("Não foi possivel encontrar o Anexo I.pdf");
        }
    }
}
