package application.model.output;

import application.model.Logable;

/**
 * Alle klasser, som implementerer dette interface, skal have et navn, som slutter på "Logger" (f.eks. FilLogger)
 */
public interface Logger {
    public void log(Logable object);
}
