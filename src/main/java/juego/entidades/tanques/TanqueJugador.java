package juego.entidades.tanques;

import juego.entidades.TipoEnte;
import juego.eventos.EventoManager;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

ublic class TanqueJugador extends Tanque {
    private boolean invulnerable;
    private boolean disparoMejorado;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccion,
                         int vida, int danio, int velocidad, int velocidadDeDisparo, Direccion direccionInicial) {
        super(posicion, dimensiones, vida, danio, velocidad, velocidadDeDisparo, direccionInicial);
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();
            Coordenada origen = new Coordenada(
                    getPosicion().getX() + getDimensiones().getAncho() / 2,
                    getPosicion().getY() + getDimensiones().getAlto() / 2
            );
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();
            return new Bala(getDireccion(), danioDisparo, origen, new Dimensiones(8, 8), 8.0);
        }
        return null;
    }

    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) super.recibirDanio(cantidad);
    }

    // Getters / Setters controlados por GestorPowerUp
    public void setInvulnerabilidad(boolean valor) { this.invulnerable = valor; }
    public void setDisparoMejorado(boolean valor) { this.disparoMejorado = valor; }

    public boolean isInvulnerable() { return invulnerable; }
    public boolean isDisparoMejorado() { return disparoMejorado; }

    @Override
    public TipoEnte getTipo() { return TipoEnte.JUGADOR; }
}


