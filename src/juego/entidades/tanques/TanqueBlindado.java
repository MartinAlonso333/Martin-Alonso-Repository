package juego.entidades.tanques;
import juego.entidades.entes.Ente;

public class TanqueBlindado extends Tanque {
    public TanqueBlindado(Ente enteControdlador) {
        super(enteControdlador,30,10);
    }

    @Override
    public void mover() {
        System.out.println("Tanque Blindado se mueve con normalidad");
    }

    @Override
    public void disparar(){
        if (puedeDisparar(2000)){
            registrarDisparo();
        }
    }
}
