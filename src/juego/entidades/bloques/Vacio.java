package juego.entidades.bloques;

public class Vacio extends Bloque {
    public BloqueVacio(Coordenada pos) {
        super(pos, 0, TipoBloque.VACIO);
    }

    @Override
    public boolean permitePaso() {
        return true;
    }

    @Override
    public boolean permiteDisparo() {
        return true;
    }

    @Override
    public void recibirDisparo() {
        // nada que hacer
    }

    @Override
    public boolean estaDestruido() {
        return true;
    }
}
