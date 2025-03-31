package com.github.josiasdev;

import com.github.josiasdev.webScraping.view.ConsoleView;
import com.github.josiasdev.webScraping.model.AnexoService;
import com.github.josiasdev.webScraping.controller.AnexoController;
public class Main {
    public static void main(String[] args) {
        AnexoService anexoService = new AnexoService();
        ConsoleView consoleView = new ConsoleView();
        AnexoController anexoController = new AnexoController(anexoService, consoleView);
        anexoController.processar();
    }
}