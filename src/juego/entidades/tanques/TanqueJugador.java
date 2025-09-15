package juego.entidades.tanques;

import juego.entidades.entes.Ente;

public class TanqueJugador extends Tanque {
    private boolean invulnerable;
    private long tiempoInvulnerable;
    private boolean disparoMejorado;

    public TanqueJugador(Ente entecontrolador) {
        super(entecontrolador, 30, 10); // valores iniciales del jugador
        this.invulnerable = false;
        this.disparoMejorado = false;
    }

    @Override
    public void disparar(){
        if (puedeDisparar(2000)) {
            if (disparoMejorado) {
                System.out.println("Disparo mejorado");
            }
            registrarDisparo();
        }
    }

    @Override
    public void mover(){
        // Movimiento controlado por input del jugador
    }


    public void activarInvulnerabilidad(int ms) {
        invulnerable = true;
        tiempoInvulnerable = System.currentTimeMillis() + ms;
    }
    public void actualizarEstado() {
        if (invulnerable && System.currentTimeMillis() > tiempoInvulnerable) {
            invulnerable = false;
        }
    }
    public void mejorarDisparo() {
        disparoMejorado = true;
    }

    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) {
            super.recibirDanio(cantidad);
        } else {
            System.out.println("Casco activo: sin daño recibido");
        }
    }
}
