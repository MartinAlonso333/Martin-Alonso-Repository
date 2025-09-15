package juego.entidades.entes;

public class Jugador extends Ente {
    public Jugador(String nombre) {
        super(nombre, "jugador");
        this.puntaje = 0;
    }

    public void mostrarInfo() {
        System.out.println("Jugador: " + getNombre() + " | Puntaje: " + puntaje);
    }
}
