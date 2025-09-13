package juego.entidades.bloques;

public class Acero extends Bloque {
    public Acero(Coordenada pos) {
        super(pos, Integer.MAX_VALUE, TipoBloque.ACERO);
    }

    @Override
    public boolean permitePaso() {
        return false;
    }

    @Override
    public boolean permiteDisparo() {
        return false;
    }

    @Override
    public void recibirDisparo() {

    }

    @Override
    public boolean esIndestructible() {
        return true;
    }
}
