package org.modelo.entidades.tanques;

import org.junit.jupiter.api.Test;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Direccion;

import static org.junit.jupiter.api.Assertions.*;

class TanqueJugadorTest {

    @Test
    void tanqueSeMueveCorrectamente() {
        TanqueJugador tanque = new TanqueJugador(new Coordenada(0, 0));
        tanque.mover(Direccion.DERECHA);

        assertEquals(1, tanque.getCoordenada().getX());
        assertEquals(0, tanque.getCoordenada().getY());
    }

    @Test
    void tanqueDisparaGeneraBala() {
        TanqueJugador tanque = new TanqueJugador(new Coordenada(0, 0));
        assertNotNull(tanque.disparar());
    }
}
