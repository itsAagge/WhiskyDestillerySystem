package application.model.output;

import application.model.Logable;
import java.io.File;
import java.io.PrintWriter;

public class FilLogger implements Logger {

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
