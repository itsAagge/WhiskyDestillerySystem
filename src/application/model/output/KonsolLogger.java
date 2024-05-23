package application.model.output;

import application.model.Logable;

public class KonsolLogger implements Logger {
    @Override
    public void log(Logable object) {
        System.out.println(object.getBeskrivelse());
    }
}
