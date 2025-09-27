package org.modelo.entidades.tanques;


import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public class TanqueBlindado extends TanqueEnemigo {

    public TanqueBlindado(Coordenada posicion, Dimensiones dimensiones, Direccion direccion) {
        super(posicion, dimensiones, 3, 1, 1,2,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

}
