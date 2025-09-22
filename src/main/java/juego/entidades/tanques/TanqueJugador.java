package main.java.juego.entidades.tanques;


import main.java.juego.entidades.TipoEnte;
import main.java.juego.utilidades.Coordenada;
import main.java.juego.utilidades.Dimensiones;
import main.java.juego.utilidades.Direccion;
import main.java.juego.utilidades.EventManager;

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

            // Determinar punto de salida (centro del tanque)
            Coordenada origen = new Coordenada(
                    getPosicion().getX() + getDimensiones().ancho() / 2,
                    getPosicion().getY() + getDimensiones().alto() / 2
            );
            // Elegir daño según si es mejorado o no
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();
            Bala bala = new Bala(getDireccion(), danioDisparo, origen, new Dimensiones(8, 8), 8.0);
            // Notificar al EventManager que hay una nueva bala
            EventManager.getInstancia().notificar("nueva_bala", bala);

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
