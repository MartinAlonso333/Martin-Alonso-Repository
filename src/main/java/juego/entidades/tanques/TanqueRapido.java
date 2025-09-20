package juego.entidades.tanques;


import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueRapido extends TanqueEnemigo {

    public TanqueRapido(Coordenada posicion, Dimensiones dimensiones, String spriteNormal, String spriteDestruido) {
        super(posicion, dimensiones, 1, 1, spriteNormal, spriteDestruido, 3);
    }

    @Override
    public void mover(Direccion dr) {
        super.mover(dr);
    }

    @Override
    public void disparar() {
        if (puedeDisparar(3000)) {
            registrarDisparo();
        }
    }
}
