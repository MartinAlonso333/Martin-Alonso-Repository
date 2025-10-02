package org.modelo.entidades.bloques;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Dimensiones;

import static org.junit.jupiter.api.Assertions.*;

class BloqueTest {

    private Bloque ladrillo;
    private Bloque base;
    private Bloque bosque;
    private Bloque acero;

    @BeforeEach
    void setUp() {
        ladrillo = new Bloque(TipoBloque.LADRILLO, new Coordenada(0,0), new Dimensiones(20,20));
        base = new Bloque(TipoBloque.BASE, new Coordenada(0,0), new Dimensiones(20,20));
        bosque = new Bloque(TipoBloque.BOSQUE, new Coordenada(0,0), new Dimensiones(20,20));
        acero = new Bloque(TipoBloque.ACERO, new Coordenada(0,0), new Dimensiones(20,20));
    }

    @Test
    void propiedadesIniciales() {
        // Vida inicial
        assertEquals(3, ladrillo.getVida());
        assertEquals(1, base.getVida());
        assertEquals(0, bosque.getVida());
        assertEquals(0, acero.getVida());

        // Destructibilidad
        assertTrue(ladrillo.esDestructible());
        assertTrue(base.esDestructible());
        assertFalse(bosque.esDestructible());
        assertFalse(acero.esDestructible());

        // Paso
        assertFalse(ladrillo.permitePaso());
        assertFalse(base.permitePaso());
        assertTrue(bosque.permitePaso());
        assertFalse(acero.permitePaso());

        // Bala impacta
        assertTrue(ladrillo.balaimpacta());
        assertTrue(base.balaimpacta());
        assertFalse(bosque.balaimpacta());
        assertTrue(acero.balaimpacta());
    }

    @Test
    void recibirDanioBloqueDestructible() {
        ladrillo.recibirDanio(1);
        assertEquals(2, ladrillo.getVida());
        assertTrue(ladrillo.estaActivo());
        assertFalse(ladrillo.estaDestruido());

        ladrillo.recibirDanio(2);
        assertEquals(0, ladrillo.getVida());
        assertFalse(ladrillo.estaActivo());
        assertTrue(ladrillo.estaDestruido());
    }

    @Test
    void recibirDanioBloqueNoDestructible() {
        int vidaInicial = acero.getVida();
        acero.recibirDanio(10);
        assertEquals(vidaInicial, acero.getVida(), "Bloque no destructible no debería perder vida");
        assertTrue(acero.estaActivo());
        assertFalse(acero.estaDestruido());
    }

    @Test
    void recibirDanioBase() {
        base.recibirDanio(1);
        assertEquals(0, base.getVida());
        assertFalse(base.estaActivo());
        assertTrue(base.estaDestruido());
    }
}
