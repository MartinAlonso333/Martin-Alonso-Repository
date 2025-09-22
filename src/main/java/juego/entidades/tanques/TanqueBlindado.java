package main.java.juego.entidades.tanques;


import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;

public class TanqueBlindado extends TanqueEnemigo {

    public TanqueBlindado(Coordenada posicion, Dimensiones dimensiones, String spriteNormal, String spriteDestruido,Direccion direccion) {
        super(posicion, dimensiones, 3, 1, 1,2,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

}
