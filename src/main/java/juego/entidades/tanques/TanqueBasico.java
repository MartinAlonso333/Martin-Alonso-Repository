package main.java.juego.entidades.tanques;


import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;

public class TanqueBasico extends TanqueEnemigo {

    public TanqueBasico(Coordenada posicion, Dimensiones dimensiones, String spriteNormal, String spriteDestruido,Direccion direccion) {
        super(posicion, dimensiones, 1, 1, 1,3,direccion);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

}
