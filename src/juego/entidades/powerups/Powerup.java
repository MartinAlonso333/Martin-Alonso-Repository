package juego.entidades.powerups;

public class PowerUp {
    protected Coordenada posicion;
    protected String nombre;
    protected boolean activo;
    protected TipoPowerUp tipo;

    private final Runnable efecto;

    public PowerUp(Coordenada posicion, String nombre, TipoPowerUp tipo) {
        this.posicion = posicion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.activo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActivo() {
        return activo;

    }
    public void desactivar() {
        activo = false;
    }

    public Coordenada getPosicion() {
        return posicion;
    }

    public TipoPowerUp getTipo() {
        return tipo;
    }

    public void recoger() {
        if (!activo) return;
        efecto.run();   // ejecuta la lógica del PowerUp
        desactivar();
    }
}