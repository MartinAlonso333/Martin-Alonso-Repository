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

    private static final int DURACION_DISPARO_MEJORADO = 10000;
    private static final int DURACION_INVULNERABILIDAD = 10000;

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
            EventoManager.getInstancia().notificar("bala_disparada", bala);
            return bala;
        }
        return null;
    }

    public void activarInvulnerabilidad() {
        invulnerable = true;
        tiempoInvulnerable = System.currentTimeMillis() + DURACION_INVULNERABILIDAD;
    }

    public void mejorarDisparo() {
        disparoMejorado = true;
        tiempoDisparoMejorado = System.currentTimeMillis() + DURACION_DISPARO_MEJORADO;
    }

    @Override
    public void actualizar() {
        long ahora = System.currentTimeMillis();
        if (invulnerable && ahora > tiempoInvulnerable) invulnerable = false;
        if (disparoMejorado && ahora > tiempoDisparoMejorado) disparoMejorado = false;
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
