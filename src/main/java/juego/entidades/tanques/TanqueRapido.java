package juego.entidades.tanques;


import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueRapido extends TanqueEnemigo {

    public TanqueRapido(Coordenada posicion, Dimensiones dimensiones, Direccion direccion) {
        super(posicion, dimensiones, 1, 1, 3,2,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }
}
