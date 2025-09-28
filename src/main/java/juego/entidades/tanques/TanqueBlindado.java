package juego.entidades.tanques;


import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueBlindado extends TanqueEnemigo {

    public TanqueBlindado(Coordenada posicion, Dimensiones dimensiones, Direccion direccion) {
        super(posicion, dimensiones, 3, 1, 1,2,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }
    @Override
    public TipoEnte getTipo() { return TipoEnte.TANQUE_BLINDADO; }
}
