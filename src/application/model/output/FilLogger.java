package application.model.output;

import application.controller.Controller;
import application.model.Logable;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.PrintWriter;

public class FilLogger implements Logger {

    private Controller controller = Controller.getController();

    public FilLogger() {
    }

    @Override
    public void log(Logable object) {
        File file = new File("resources/beskrivelser/" + object.getFileName() + ".txt");
        try {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(object.getBeskrivelse());
            printWriter.close();
            controller.opretAlert(Alert.AlertType.INFORMATION, "Succes", "Beskrivelse printet succesfuldt til resources/beskrivelser/" + object.getFileName() + ".txt");
        } catch (Exception e) {
            controller.opretAlert(Alert.AlertType.ERROR, "Fejl", "Der opstod en fejl i oprettelsen af filen. Prøv igen senere");
        }
    }
}
