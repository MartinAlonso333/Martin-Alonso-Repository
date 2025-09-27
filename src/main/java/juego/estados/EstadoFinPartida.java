package juego.estados;

public class EstadoFinPartida implements EstadoJuego {

    private final GestorEstados gestor;

    public EstadoFinPartida(GestorEstados gestor) {
        this.gestor = gestor;
    }

    @Override
    public void actualizar(double deltaTime) {
        // Podés mostrar mensaje, animaciones, etc.
    }

    @Override
    public void manejarInput(String input) {
        if ("ENTER".equals(input)) {
            gestor.cambiarAMenu();
        }
    }
}
