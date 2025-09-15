package juego.entidades.tanques;

import juego.entidades.entes.Ente;

public class TanquePotente extends Tanque{
    public TanquePotente(Ente enteControlador) {
        super(enteControlador,10,10);
    }

    @Override
    public void mover() {
        System.out.println("Tanque potente se mueve con normalidad");
    }

    @Override
    public void disparar(){
        if (puedeDisparar(1000)){
            registrarDisparo();
        }
    }
}
