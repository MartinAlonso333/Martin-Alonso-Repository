package juego.entidades.tanques;
import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;

public class TanqueJugador extends Tanque {
    private boolean invulnerable;
    private long tiempoInvulnerable;
    private boolean disparoMejorado;
    private long tiempoDisparoMejorado;
    private static final int DURACION_DISPARO_MEJORADO = 10000;
    private static final int INTERVALO_DISPARO_MS = 2000;
    private static final int DURACION_INVULNERABILIDAD = 10000;


    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, String spriteNormal, String spriteDestruido, int velocidad) {
        super(posicion,dimensiones, 3, 1, spriteNormal, spriteDestruido, velocidad);
        this.invulnerable = false;
        this.disparoMejorado = false;
    }

    @Override
    public void disparar() {
        if (puedeDisparar(INTERVALO_DISPARO_MS)) {
            if (disparoMejorado) {
                System.out.println("Disparo mejorado");
            } else {
                System.out.println("Disparo normal");
            }
            registrarDisparo();
        }
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


    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) {
            super.recibirDanio(cantidad);
        } else {
            System.out.println("Casco activo: sin daño recibido");
        }
    }

    private void mejorarDisparo() {
        disparoMejorado = true;
        tiempoDisparoMejorado = System.currentTimeMillis() + DURACION_DISPARO_MEJORADO;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isDisparoMejorado() {
        return disparoMejorado;
    }
    private void actualizarDisparoMejorado() {
        if (disparoMejorado && System.currentTimeMillis() > tiempoDisparoMejorado) {
            disparoMejorado = false;
            System.out.println("El disparo mejorado se terminó");
        }
    }

    @Override
    public void actualizar() {
        super.actualizar();      // mantiene el movimiento y la lógica base de Tanque
        actualizarEstado();      // chequea si sigue siendo invulnerable
        actualizarDisparoMejorado(); // chequea si sigue activo el disparo mejorado
    }

    @Override
    public TipoEnte getTipo() {
        return null;
    }
}
