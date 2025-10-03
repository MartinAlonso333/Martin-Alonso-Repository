package org.modelo.utilidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordenadaTest {

    private Coordenada coord;

    @BeforeEach
    void setUp() {
        coord = new Coordenada(10, 10);
    }

    @Test
    void cambiarCoordenadaValida() {
        coord.setCoordenada(15, 25);
        assertEquals(15, coord.getPixelX());
        assertEquals(25, coord.getPixelY());
    }

    @Test
    void lanzaExcepcionSiXNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            coord.setCoordenada(-1, 5);
        });
        assertTrue(exception.getMessage().contains("x=-1"));
    }

    @Test
    void lanzaExcepcionSiYNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            coord.setCoordenada(5, -2);
        });
        assertTrue(exception.getMessage().contains("y=-2"));
    }

    @Test
    void lanzaExcepcionSiAmbasNegativas() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            coord.setCoordenada(-3, -7);
        });
        assertTrue(exception.getMessage().contains("x=-3") && exception.getMessage().contains("y=-7"));
    }
}
