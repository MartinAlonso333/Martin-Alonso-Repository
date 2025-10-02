package org.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelo.colisiones.SistemaColisionGrilla;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.Tanque;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.entidades.bloques.Bloque;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

import static org.junit.jupiter.api.Assertions.*;

class SistemaColisionGrillaTest {

    private SistemaColisionGrilla sistema;
    private TanqueJugador jugador;
    private Bloque ladrillo;

    @BeforeEach
    void setUp() {
        sistema = new SistemaColisionGrilla();
        jugador = new TanqueJugador(new Coordenada(0,0), new Dimensiones(20,20), Direccion.DERECHA, 1, TipoTanque.JUGADOR1);
        ladrillo = new Bloque(TipoBloque.LADRILLO, new Coordenada(0,0), new Dimensiones(20,20));
    }

    @Test
    void balaImpactaBloque() throws InterruptedException {
        // Forzar que pueda disparar
        try {
            var field = Tanque.class.getDeclaredField("ultimoDisparo");
            field.setAccessible(true);
            field.setLong(jugador, 0L);
        } catch (Exception e) {
            fail("No se pudo forzar ultimoDisparo: " + e.getMessage());
        }

        var bala = jugador.disparar();
        assertNotNull(bala);


        bala.setPosicion(new Coordenada(0,0));

        sistema.agregarEnte(bala);
        sistema.agregarEnte(ladrillo);

        sistema.chequearColisiones(bala);

        assertFalse(bala.estaActivo(), "La bala debería desactivarse al impactar el bloque");
        assertTrue(ladrillo.getVida() < ladrillo.getTipoBloque().getVidaInicial(),
                "El bloque debería haber recibido daño");
    }

    @Test
    void balaImpactaBloqueNoDestructible() {
        var acero = new Bloque(TipoBloque.ACERO, new Coordenada(0,0), new Dimensiones(20,20));

        try {
            var field = Tanque.class.getDeclaredField("ultimoDisparo");
            field.setAccessible(true);
            field.setLong(jugador, 0L);
        } catch (Exception e) { fail(e); }

        var bala = jugador.disparar();
        bala.setPosicion(new Coordenada(0,0));

        sistema.agregarEnte(bala);
        sistema.agregarEnte(acero);
        sistema.chequearColisiones(bala);

        assertFalse(bala.estaActivo(), "La bala se desactiva");
        assertEquals(acero.getTipoBloque().getVidaInicial(), acero.getVida(), "Bloque no destructible no pierde vida");
    }

    @Test
    void tanqueRecogePowerUp() {
        var powerUp = new org.modelo.entidades.powerups.PowerUp(new Coordenada(0,0),
                new Dimensiones(20,20), TipoPowerUp.CASCO);
        sistema.agregarEnte(jugador);
        sistema.agregarEnte(powerUp);

        sistema.chequearColisiones(jugador);
        assertTrue(jugador.isInvulnerable());
        assertFalse(powerUp.estaActivo(), "El power-up debe desactivarse al ser recogido");
    }


}
