package juego.entidades.tanques;

import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueJugador extends Tanque {

    private boolean invulnerable;
    private long tiempoInvulnerable;
    private boolean disparoMejorado;
    private long tiempoDisparoMejorado;

    private static final int DURACION_DISPARO_MEJORADO = 10000;
    private static final int INTERVALO_DISPARO_MS = 2000;
    private static final int DURACION_INVULNERABILIDAD = 10000;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, int vida, int danio, int velocidad) {
        super(posicion, dimensiones, vida, danio, velocidad);
    }

    @Override
    public void disparar() {
        if (puedeDisparar(INTERVALO_DISPARO_MS)) {
            if (disparoMejorado) System.out.println("Disparo mejorado");
            else System.out.println("Disparo normal");
            registrarDisparo();
        }
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
