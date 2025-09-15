package juego.entidades.tanques;
import juego.entidades.entes.Ente;

public class TanqueRapido extends Tanque{

    public TanqueRapido(Ente entecontrolador) {
        super(entecontrolador,10,10);
    }

    @Override
    public void mover() {
        System.out.println("Tanque rapido se mueve rapidamente.");
    }

    @Override
    public void disparar(){
        if (puedeDisparar(2000)){
            registrarDisparo();
        }
    }
}
