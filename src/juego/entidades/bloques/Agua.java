package juego.entidades.bloques;

public class Agua extends Bloque {
    public Agua(Coordenada pos) {
        super(pos, Integer.MAX_VALUE, TipoBloque.AGUA);
    }

    @Override
    public boolean permitePaso() {
        return false; // tanque no puede pasar
    }

    @Override
    public boolean permiteDisparo() {
        return true;  // balas atraviesan
    }

    @Override
    public void recibirDisparo() {
        // no hace nada
    }
}
