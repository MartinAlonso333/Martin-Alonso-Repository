package org.modelo.entidades.tanques;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;
import org.modelo.utilidades.Direccion;

import static org.junit.jupiter.api.Assertions.*;

class TanqueJugadorTest {

    private TanqueJugador tanque;

    @BeforeEach
    void setUp() {
        tanque = new TanqueJugador(
                new Coordenada(10.0, 10.0),
                new Dimensiones(20, 20),
                Direccion.DERECHA,
                1,
                TipoTanque.JUGADOR1
        );
    }
    @Test
    void recibeDanioReduceVida() {
        int vidaInicial = tanque.getVida();
        tanque.recibirDanio(1);
        assertEquals(vidaInicial - 1, tanque.getVida(), "La vida debería disminuir correctamente");
    }

    @Test
    void recibeDanioInvulnerableNoReduceVida() {
        tanque.setInvulnerabilidad(true);
        int vidaInicial = tanque.getVida();
        tanque.recibirDanio(2);
        assertEquals(vidaInicial, tanque.getVida(), "Si está invulnerable, la vida no debería cambiar");
    }

    @Test
    void tanqueCambiaDireccion() {
        tanque.mover(Direccion.IZQUIERDA);

        assertEquals(Direccion.IZQUIERDA, tanque.getDireccion());
    }



    @Test
    void tanqueDisparaGeneraBala() {
        GestorEventos em = new EventoManager();
        // Reflexion para forzar el tiempo de último disparo a 0 y evitar restricciones de tiempo
        try {
            java.lang.reflect.Field field = Tanque.class.getDeclaredField("ultimoDisparo");
            field.setAccessible(true);
            field.setLong(tanque, 0L);
        } catch (Exception e) {
            fail("No se pudo forzar el ultimoDisparo: " + e.getMessage());
        }

        // Dispara sin restricciones de tiempo
        assertNotNull(tanque.disparar(GestorEventos em), "El tanque debería generar una bala al disparar");
    }

    @Test
    void tanqueSeMueve() {
        tanque.mover(Direccion.DERECHA);
        tanque.actualizar(1.0);

        double nuevaX = tanque.getPosicion().getPixelX();
        double nuevaY = tanque.getPosicion().getPixelY();

        assertNotEquals(10.0, nuevaX, "El tanque debería haberse movido en X");
        assertEquals(10.0, nuevaY, "El tanque no debería haberse movido en Y");
    }
}
