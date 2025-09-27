package org.modelo.entidades.tanques;

import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueBasico extends TanqueEnemigo {

    public TanqueBasico(Coordenada posicion, Dimensiones dimensiones, Direccion direccion) {
        super(posicion, dimensiones, 1, 1, 1,3,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

}
