package gui;

import application.controller.Controller;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        initContent();
        Application.launch(MainWindow.class);
    }

    private static void initContent() {
        Controller.opretFad("Spanien", "Cherry", 30, "Eg", 9.5);
        Controller.opretFad("Spanien", "Cherry", 250, "Eg", 1.0);
        Controller.opretFad("Spanien", "Bourbon", 30, "Eg", 9.5);
        Controller.opretFad("Spanien", "Bourbon", 250, "Eg", 5);
        Controller.opretFad("Spanien", "Bourbon", 100, "Eg", 9.5);
    }
}
