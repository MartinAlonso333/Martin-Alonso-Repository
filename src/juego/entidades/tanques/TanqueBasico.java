package juego.entidades.tanques;
import juego.entidades.entes.Ente;

public class TanqueBasico extends Tanque {

    public TanqueBasico(Ente entecontrolador) {
        super(entecontrolador,10,10);
    }

    @Override
    public void mover() {
        System.out.println("Tanque básico se mueve despacio.");
    }

    @Override
    public void disparar(){
        if (puedeDisparar(2000)){
            registrarDisparo();
        }
    }
}
