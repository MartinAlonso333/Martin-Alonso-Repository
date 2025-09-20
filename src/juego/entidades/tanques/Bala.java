package juego.entidades.tanques;

import juego.entidades.Ente;
import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;


public class Bala extends Ente {
    private final String imagen;
    private final String direccion;
    private int danio;
    private boolean activo;
    private int velocidad;

    public Bala(String imagen, String direccion,int  danio, String tipo, int velocidad,Coordenada posicion, Dimensiones dimensiones) {
        super(posicion, dimensiones);
        this.imagen = imagen;
        this.direccion = direccion;
        this.danio = danio;
        this.velocidad = velocidad;
        this.activo = true;
    }

    public int getDanio() {return danio;}
    public boolean estaActivo() {return activo;}

    public void setDanio(int danio) {this.danio = danio;}
    public void setActivo(boolean activo) {this.activo = activo;}

    @Override
    public void actualizar(double deltaTime) {
        // Ejemplo simple de movimiento según la dirección
        switch (direccion) {
            case "arriba" -> posicion.setY(posicion.getY() - velocidad);
            case "abajo"  -> posicion.setY(posicion.getY() + velocidad);
            case "izquierda" -> posicion.setX(posicion.getX() - velocidad);
            case "derecha" -> posicion.setX(posicion.getX() + velocidad);
        }
    }

    @Override
    public TipoEnte getTipo() {
        return TipoEnte.BALA;
    }

    @Override
    public boolean estaDestruido() {
        return !estaActivo();
    }
}
