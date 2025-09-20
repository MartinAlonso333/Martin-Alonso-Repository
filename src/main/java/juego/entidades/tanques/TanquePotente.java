package juego.entidades.tanques;


import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanquePotente extends TanqueEnemigo {

    public TanquePotente(Coordenada posicion, Dimensiones dimensiones, String spriteNormal, String spriteDestruido) {
        super(posicion, dimensiones, 1, 1, spriteNormal, spriteDestruido, 1);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

    @Override
    public void disparar() {
        if (puedeDisparar(1000)) { //
            registrarDisparo();
        }
    }
}
