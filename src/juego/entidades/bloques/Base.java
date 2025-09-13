package juego.entidades.bloques;

public class Base extends Bloque {
    public Base(Coordenada pos) {
        super(pos, 1, TipoBloque.BASE);
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
        vida = 0; // destruida inmediatamente
        // notificar al juego que se perdió
    }
}
