package application.model.output;

import application.controller.Controller;
import application.model.Logable;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FilLogger implements Logger {
    private Controller controller = Controller.getController();

    @Override
    public void log(Logable object) {
        File file = new File("resources/beskrivelser/" + object.getFileName() + ".txt");
        try {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(object.getBeskrivelse());
            printWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
