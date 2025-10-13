package org.modelo.entidades.tanques;

import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

public abstract class Tanque extends Ente {
    private int vida;
    private final int danio;
    private Coordenada ultimaPosicion;
    private long ultimoDisparo;
    protected final double velocidad;         // px/s
    protected final int velocidadDeDisparo;   // ms
    protected Direccion direccion;
    private long tiempoQuieto = 0;
    private boolean estaMoviendo = false;
    protected final TipoTanque tipo;
    protected final GestorEventos em;

    public Tanque(Coordenada posicion, Dimensiones dimensiones, TipoTanque tipo, Direccion direccionInicial, GestorEventos gestorEventos) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVida();
        this.danio = tipo.getDanio();
        this.velocidad = tipo.getVelocidad();
        this.velocidadDeDisparo = tipo.getVelocidadDisparo();
        this.ultimoDisparo = System.currentTimeMillis();
        this.direccion = direccionInicial;
        em = gestorEventos;
    }

    public abstract Bala disparar();

    protected Coordenada getPuntoDeDisparo() {
        return new Coordenada(
                getPosicion().getPixelX() + (double) getDimensiones().getAncho() / 2,
                getPosicion().getPixelY() + (double) getDimensiones().getAlto() / 2
        );
    }

    public void mover(Direccion dir) {
        if (System.currentTimeMillis() < tiempoQuieto) {
            estaMoviendo = false;
            return;
        }
        this.direccion = dir;
        estaMoviendo = true;
    }

    public void detenerMovimiento() { estaMoviendo = false; }

    @Override
    public boolean estaMoviendo() {
        return estaMoviendo;
    }

    @Override
    public void actualizar(double deltaTime) {
        if (!estaMoviendo) return;

        ultimaPosicion = new Coordenada(posicion.getPixelX(), posicion.getPixelY());
        Coordenada nuevaPos = new Coordenada(posicion.getPixelX(), posicion.getPixelY());

        direccion.aplicarMovimiento(nuevaPos, velocidad * deltaTime);
        posicion.setCoordenada(nuevaPos.getPixelX(), nuevaPos.getPixelY());
    }

    public void aturdir(long duracionMs) {
        tiempoQuieto = System.currentTimeMillis() + duracionMs;
        estaMoviendo = false;
    }

    public boolean puedeDisparar() {
        return System.currentTimeMillis() - ultimoDisparo >= velocidadDeDisparo;
    }

    protected void registrarDisparo() { ultimoDisparo = System.currentTimeMillis(); }

    public int getVida() { return vida; }
    public int getDanio() { return danio; }
    public TipoTanque getTipoTanque() { return tipo; }
    public Direccion getDireccion() { return direccion; }

    public Coordenada getUltimaPosicion() {
        return ultimaPosicion;
    }

    public void impactoConBala(Bala bala, GestorEventos em) {
        if (bala.getDuenio() == this) return;

        if (bala.getDuenio().getTipoEnte() == TipoEnte.JUGADOR && this.getTipoEnte() == TipoEnte.JUGADOR) {
            aturdir(2000);  // TIEMPOATURDIDO
            bala.setActivo(false);
            return;
        }

        recibirDanio(bala.getDanio());
        bala.setActivo(false);

        emitirEventoImpacto(em);
    }
    public void emitirEventoImpacto(GestorEventos em) {
        getTipoTanque().emitirEvento(em, this);
    }


    public void recibirDanio(int cantidad) {
        if (vida <= 0) return;
        vida -= cantidad;
        if (vida <= 0) destruir();
    }

    public void destruir() {
        vida = 0;
        setActivo(false);
        em.notificar(TipoEvento.TANQUE_DESTRUIDO, this);
    }

    @Override
    public boolean estaDestruido() { return vida <= 0; }
}
