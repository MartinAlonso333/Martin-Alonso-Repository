package juego.entidades.tanques;


import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class Bala extends Ente {
    private final Direccion direccion;
    private int danio;
    private boolean activo;
    private final double velocidad;


    public Bala(Direccion direccion, int danio, Coordenada posicion, Dimensiones dimensiones, double velocidad) {
        super(posicion, dimensiones);
        this.direccion = direccion;
        this.danio = danio;
        this.velocidad = velocidad;
        this.activo = true;
    }

    public int getDanio() { return danio; }
    public boolean estaActivo() { return activo; }
    public Direccion getDireccion() { return direccion; }

    public void setDanio(int danio) { this.danio = danio; }
    public void setActivo(boolean activo) { this.activo = activo; }


    @Override
    public void actualizar() { direccion.aplicarMovimiento(posicion, velocidad); }

    @Override
    public TipoEnte getTipo() { return TipoEnte.BALA; }

    @Override
    public boolean estaDestruido() { return !activo; }
}
