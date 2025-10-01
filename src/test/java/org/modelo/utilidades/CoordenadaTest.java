package org.modelo.utilidades;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CoordenadaTest {

    private Coordenada coord;

    @BeforeEach
    void setUp() {
        coord = new Coordenada(10, 10);
    }

    @Test
    void cambiarCoordenadaX() {
        coord.setCoordenada(coord.getPixelX() + 3, coord.getPixelY());
        assertEquals(8, coord.getPixelX());
        assertEquals(10, coord.getPixelY());
    }

    @Test
    void cambiarCoordenadaY() {
        coord.setCoordenada(coord.getPixelX(), coord.getPixelY() - 4);
        assertEquals(10, coord.getPixelX());
        assertEquals(6, coord.getPixelY());
    }

    @Test
    void cambiarCoordenadaAmbas() {
        coord.setCoordenada(coord.getPixelX() - 2, coord.getPixelY() + 5);
        assertEquals(8, coord.getPixelX());
        assertEquals(15, coord.getPixelY());
    }
}
