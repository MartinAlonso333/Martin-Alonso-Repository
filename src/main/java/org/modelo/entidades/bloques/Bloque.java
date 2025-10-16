package org.modelo.entidades.bloques;

import org.modelo.entidades.ConSprite;
import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.entidades.tanques.Bala;
import org.modelo.eventos.GestorEventos;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;

public class Bloque extends Ente{
    private final TipoBloque tipo;
    private int vida;

    public Bloque(TipoBloque tipo, Coordenada posicion, Dimensiones dimensiones) {
        super(posicion, dimensiones);
        this.tipo = tipo;
        this.vida = tipo.getVidaInicial();
    }

    public int getVida() { return vida; }
    public boolean permitePaso() { return tipo.permitePaso(); }
    public boolean balaimpacta() { return tipo.balaimpacta(); }
    public boolean esDestructible() { return tipo.esDestructible(); }

    public void recibirDanio(int cantidad) {
        if (tipo.esDestructible()) {
            vida -= cantidad;
            if (estaDestruido()) setActivo(false);
        }
    }

    public void impactoConBala(Bala bala, GestorEventos em) {
        if (!getTipoBloque().balaimpacta()) return;

        recibirDanio(bala.getDanio());
        bala.setActivo(false);

        emitirEventoImpacto(em);
    }

    public void emitirEventoImpacto(GestorEventos em) {
        getTipoBloque().emitirEvento(em, this);
    }

    @Override
    public void actualizar(double deltaTime) {
        // Los bloques no se mueven, no hay nada que actualizar
    }

    @Override
    public boolean estaDestruido() { return tipo.esDestructible() && vida <= 0; }

    @Override
    public TipoEnte getTipoEnte() { return TipoEnte.BLOQUE; }

    public TipoBloque getTipoBloque() { return tipo; }

    @Override
    public String getClaveSprite() {
        return getTipoBloque().getClaveSprite();
    }
}
