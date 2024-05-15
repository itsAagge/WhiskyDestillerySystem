package application.model.output;

import application.controller.Controller;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.PrintWriter;

public class FileLogger implements Logger {
    private final String fileLocation;

    public FileLogger(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public void log(String message) {
        File file = new File(fileLocation + ".txt");
        try {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(message);
            printWriter.close();
        } catch (Exception e) {
            Controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der opstod en fejl i oprettelsen af filen. Prøv igen senere");
        }
    }
}