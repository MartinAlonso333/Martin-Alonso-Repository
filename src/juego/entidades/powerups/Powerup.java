package juego.entidades.powerups;

public class PowerUp {
    private final TipoPowerUp tipo;
    private final Coordenada posicion;
    private boolean activo = true;

    public PowerUp(TipoPowerUp tipo, Coordenada posicion) {
        this.tipo = tipo;
        this.posicion = posicion;
    }

    public TipoPowerUp getTipo() { return tipo; }
    public Coordenada getPosicion() { return posicion; }
    public boolean isActivo() { return activo; }
    public void desactivar() { activo = false; }
}
