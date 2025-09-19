package juego.entidades;

import juego.utilidades.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Ente implements ObservableColision {

    protected Coordenada posicion;
    protected Dimensiones dimensiones;
    private boolean activo = true;

    private final List<ObserverColision> observers = new ArrayList<>();

    public Ente(Coordenada posicion, Dimensiones dimensiones) {
        this.posicion = posicion;
        this.dimensiones = dimensiones;
    }

    // Area para Colisiones
    public boolean intersecta(Ente otro) {
        return this.posicion.getX() < otro.posicion.getX() + otro.dimensiones.getAncho() &&
                this.posicion.getX() + this.dimensiones.getAncho() > otro.posicion.getX() &&
                this.posicion.getY() < otro.posicion.getY() + otro.dimensiones.getAlto() &&
                this.posicion.getY() + this.dimensiones.getAlto() > otro.posicion.getY();
    }

    // Posicion
    public Coordenada getPosicion() { return posicion; }

    public void setPosicion(Coordenada pos) {
        this.posicion = pos;
        notificarColision();
    }

    public Dimensiones getDimensiones() { return dimensiones; }

    // Estado
    public boolean estaActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // ObserverColision
    @Override
    public void agregarObserverColision(ObserverColision observer) {
        observers.add(observer);
    }

    @Override
    public void notificarColision() {
        for (ObserverColision obs : observers) {
            obs.enteSeMovio(this);
        }
    }

    // -------------------
    // Métodos abstractos
    // -------------------
    public abstract void actualizar(double deltaTime);
    public abstract TipoEnte getTipo();
    public abstract boolean estaDestruido();
}
