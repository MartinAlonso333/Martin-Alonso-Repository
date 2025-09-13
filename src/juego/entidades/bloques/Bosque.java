package juego.entidades.bloques;

public class Bosque  extends Bloque {
    public Bosque(Coordenada pos) {
        super(pos, 0, TipoBloque.BOSQUE);
    }

    @Override
    public boolean permitePaso() {
        return true;  // tanques pueden pasar
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
