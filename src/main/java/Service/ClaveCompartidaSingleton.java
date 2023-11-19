
package Service;
public class ClaveCompartidaSingleton {

    private static ClaveCompartidaSingleton instancia;
    private String claveCompartida;

    private ClaveCompartidaSingleton() {
        // Inicialización de la clave compartida
        this.claveCompartida = ""; // Aquí puedes inicializarla con un valor predeterminado si es necesario
    }

    public static synchronized ClaveCompartidaSingleton getInstance() {
        if (instancia == null) {
            instancia = new ClaveCompartidaSingleton();
        }
        return instancia;
    }

    public String getClaveCompartida() {
        return this.claveCompartida;
    }

    public void setClaveCompartida(String claveCompartida) {
        this.claveCompartida = claveCompartida;
        // Aquí podrías agregar la lógica para guardar la clave en una base de datos, archivo, o cualquier otro medio persistente
    }
}
