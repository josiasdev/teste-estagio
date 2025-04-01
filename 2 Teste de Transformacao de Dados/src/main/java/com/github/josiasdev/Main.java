package com.github.josiasdev;

import com.github.josiasdev.service.ProcedimentoService;
import com.github.josiasdev.view.ConsoleView;
import com.github.josiasdev.controller.ProcedimentoController;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        ProcedimentoService procedimentoService = new ProcedimentoService();
        ProcedimentoController procedimentoController = new ProcedimentoController(procedimentoService, view);
        procedimentoController.processar();
    }
}