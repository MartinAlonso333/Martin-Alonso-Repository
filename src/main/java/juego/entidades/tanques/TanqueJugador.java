package juego.entidades.tanques;

import juego.entidades.TipoEnte;
import juego.utilidades.Coordenada;
import juego.utilidades.Dimensiones;
import juego.utilidades.Direccion;

public class TanqueJugador extends Tanque {

    private boolean invulnerable;
    private boolean disparoMejorado;

    public TanqueJugador(Coordenada posicion, Dimensiones dimensiones, Direccion direccion, int vida, int danio, int velocidad, int velocidadDeDisparo,Direccion direccionInicial) {
        super(posicion, dimensiones, 3, 1, 1,2,direccionInicial);
    }

    @Override
    public Bala disparar() {
        if (puedeDisparar(velocidadDeDisparo)) {
            registrarDisparo();

            // Punto de salida del disparo
            Coordenada origen = new Coordenada(
                    getPosicion().getX() + getDimensiones().getAncho() / 2,
                    getPosicion().getY() + getDimensiones().getAlto() / 2
            );
            int danioDisparo = disparoMejorado ? getDanio() * 10 : getDanio();

            return new Bala(getDireccion(), getDanio(), origen, new Dimensiones(8, 8), 8.0, this);
        }
        return null;
    }

    public void setInvulnerabilidad(boolean estado) {
        invulnerable = estado;
    }

    public void setDisparoMejorado(boolean estado) {
        disparoMejorado = estado;
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void recibirDanio(int cantidad) {
        if (!invulnerable) super.recibirDanio(cantidad);
    }

    @Override
    public TipoEnte getTipo() { return TipoEnte.JUGADOR; }
}
