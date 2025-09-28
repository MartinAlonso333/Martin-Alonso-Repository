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
        coord.setCoordenada(coord.getX() + 3, coord.getY());
        assertEquals(8, coord.getX());
        assertEquals(10, coord.getY());
    }

    @Test
    void cambiarCoordenadaY() {
        coord.setCoordenada(coord.getX(), coord.getY() - 4);
        assertEquals(10, coord.getX());
        assertEquals(6, coord.getY());
    }

    @Test
    void cambiarCoordenadaAmbas() {
        coord.setCoordenada(coord.getX() - 2, coord.getY() + 5);
        assertEquals(8, coord.getX());
        assertEquals(15, coord.getY());
    }
}
