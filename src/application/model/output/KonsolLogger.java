package application.model.output;

import application.controller.Controller;
import application.model.Logable;
import javafx.scene.control.Alert;

public class KonsolLogger implements Logger {
    Controller controller = Controller.getController();
    @Override
    public void log(Logable object) {
        System.out.println(object.getBeskrivelse());
        controller.opretAlert(Alert.AlertType.INFORMATION, "Succes", "Beskrivelse printet succesfuldt i konsollen");
    }
}
