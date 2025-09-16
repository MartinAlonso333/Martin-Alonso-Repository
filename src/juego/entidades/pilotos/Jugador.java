package juego.entidades.entes;

public class Jugador extends Piloto {
    public Jugador(String nombre) {
        super(nombre, "jugador");
        this.puntaje = 0;
    }

    public void mostrarInfo() {
        System.out.println("Jugador: " + getNombre() + " | Puntaje: " + puntaje);
    }
}
