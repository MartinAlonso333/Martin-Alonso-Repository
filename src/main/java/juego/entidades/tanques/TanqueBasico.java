package juego.entidades.tanques;

import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueBasico extends TanqueEnemigo {

    public TanqueBasico(Coordenada posicion, Dimensiones dimensiones, Direccion direccion) {
        super(posicion, dimensiones, 1, 1, 1,3,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

    @Override
    public TipoEnte getTipo() { return TipoEnte.TANQUE_BASICO; }
}
