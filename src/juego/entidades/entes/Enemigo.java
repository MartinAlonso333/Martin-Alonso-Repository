package juego.entidades.entes;

public class Enemigo extends Ente {
    private String color;

    public Enemigo(String nombre, String color) {
        super(nombre, "enemigo");
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Enemigo: " + getNombre() + " | Color: " + color);
    }
}
