package main.java.juego.entidades.tanques;


import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;

public class TanqueRapido extends TanqueEnemigo {

    public TanqueRapido(Coordenada posicion, Dimensiones dimensiones, String spriteNormal, String spriteDestruido) {
        super(posicion, dimensiones, 1, 1, 3,2);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

}
