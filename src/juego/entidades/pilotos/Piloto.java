package juego.entidades.entes;

public abstract class Ente {
    private final String nombre;
    private final String tipo;   // "jugador" o "enemigo"

    public Ente(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

}
