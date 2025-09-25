package juego.estados;

public interface EstadoJuego {
    void actualizar(double deltaTime);    // llamado cada frame
    void manejarInput(String input);      // llamado al recibir input
}
