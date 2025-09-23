package juego.entidades.tanques;

import juego.entidades.TipoEnte;
import juego.eventos.EventoManager;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueJugador extends Tanque {

    private boolean invulnerable;
    private long tiempoInvulnerable;
    private boolean disparoMejorado;
    private long tiempoDisparoMejorado;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccion, int vida, int danio, int velocidad, int velocidadDeDisparo,Direccion direccionInicial) {
        super(posicion, dimensiones, 3, 1, 1,2,direccionInicial);
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();

            // Punto de salida del disparo
            Coordenada origen = new Coordenada(
                    getPosicion().getX() + getDimensiones().getAncho() / 2,
                    getPosicion().getY() + getDimensiones().getAlto() / 2
            );
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();

            Bala bala = new Bala(getDireccion(), getDanio(), origen, new Dimensiones(8, 8), 8.0);
            return bala;
        }
        return null;
    }

    public void activarInvulnerabilidad(int duracion) {
        invulnerable = true;
        tiempoInvulnerable = System.currentTimeMillis() + duracion;
    }

    public void mejorarDisparo(int duracion) {
        disparoMejorado = true;
        tiempoDisparoMejorado = System.currentTimeMillis() + duracion;
    }

    @Override
    public void actualizar() {
        if (invulnerable && System.currentTimeMillis() > tiempoInvulnerable) {
            invulnerable = false;
        }
        if (disparoMejorado && System.currentTimeMillis() > tiempoDisparoMejorado) {
            disparoMejorado = false;
        }
    }

    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) super.recibirDanio(cantidad);
    }

    public boolean isInvulnerable() { return invulnerable; }
    public boolean isDisparoMejorado() { return disparoMejorado; }

    @Override
    public TipoEnte getTipo() { return TipoEnte.JUGADOR; }
}
