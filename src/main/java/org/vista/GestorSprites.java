package org.vista;

import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.entidades.bloques.TipoBloque;
import org.modelo.entidades.powerups.TipoPowerUp;
import org.modelo.entidades.tanques.TipoTanque;
import org.modelo.utilidades.Direccion;
import org.modelo.utilidades.Dimensiones;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.*;

public class GestorSprites {

    private static final Map<ClaveSprite, SpriteConfig> configs = new HashMap<>();
    private static final Map<String, Image> cache = new HashMap<>();
    private static final String RUTA_SPRITES = "/sprites/";

    static {
        // --- BLOQUES ---
        configs.put(new ClaveSprite(TipoEnte.BLOQUE, TipoBloque.ACERO), new SpriteConfig("base20x20", 1));
        configs.put(new ClaveSprite(TipoEnte.BLOQUE, TipoBloque.LADRILLO), new SpriteConfig("BrickBlock20x20", 1));
        configs.put(new ClaveSprite(TipoEnte.BLOQUE, TipoBloque.BASE), new SpriteConfig("base20x20", 1));
        configs.put(new ClaveSprite(TipoEnte.BLOQUE, TipoBloque.BOSQUE), new SpriteConfig("Forest20x20", 1));
        configs.put(new ClaveSprite(TipoEnte.BLOQUE, TipoBloque.AGUA), new SpriteConfig("Water20x20", 1));

        // --- ENEMIGOS ---
        configs.put(new ClaveSprite(TipoEnte.ENEMIGO, TipoTanque.BASICO), new SpriteConfig("EnemyTankRegular", 2));
        configs.put(new ClaveSprite(TipoEnte.ENEMIGO, TipoTanque.RAPIDO), new SpriteConfig("EnemyTankFast", 2));
        configs.put(new ClaveSprite(TipoEnte.ENEMIGO, TipoTanque.POTENTE), new SpriteConfig("EnemyTankPowerful", 2));
        configs.put(new ClaveSprite(TipoEnte.ENEMIGO, TipoTanque.BLINDADO), new SpriteConfig("EnemyTankHeavy", 2));

        // --- JUGADORES ---
        configs.put(new ClaveSprite(TipoEnte.JUGADOR, TipoTanque.JUGADOR), new SpriteConfig("Player1Tank", 2));
        configs.put(new ClaveSprite(TipoEnte.JUGADOR, TipoTanque.JUGADOR), new SpriteConfig("Player2Tank", 2));

        // --- BALA ---
        configs.put(new ClaveSprite(TipoEnte.BALA, null), new SpriteConfig("Shot", 1));

        // --- POWERUPS ---
        configs.put(new ClaveSprite(TipoEnte.POWERUP, TipoPowerUp.CASCO), new SpriteConfig("PowerUp-Helmet20x20", 1));
        configs.put(new ClaveSprite(TipoEnte.POWERUP, TipoPowerUp.ESTRELLA), new SpriteConfig("PowerUp-Star20x20", 1));
        configs.put(new ClaveSprite(TipoEnte.POWERUP, TipoPowerUp.GRANADA), new SpriteConfig("PowerUp-Grenade20x20", 1));
    }

    public static Map<Direccion, List<Image>> getAnimacionesPara(Ente e) {
        Map<Direccion, List<Image>> animaciones = new EnumMap<>(Direccion.class);
        ClaveSprite clave = new ClaveSprite(e.getTipoEnte(), e.getSubtipo());
        SpriteConfig config = configs.getOrDefault(clave, new SpriteConfig("default", 1));

        for (Direccion dir : Direccion.values()) {
            List<Image> frames = new ArrayList<>();
            if (config.frames > 1) {
                for (int i = 0; i < config.frames; i++) {
                    String nombre = config.nombreBase + i + "_20x20.png";
                    frames.add(obtenerSprite(nombre));
                }
            } else {
                frames.add(obtenerSprite(config.nombreBase + ".png"));
            }
            animaciones.put(dir, frames);
        }
        return animaciones;
    }

    private static Image obtenerSprite(String nombreArchivo) {
        if (cache.containsKey(nombreArchivo)) return cache.get(nombreArchivo);
        try (InputStream is = GestorSprites.class.getResourceAsStream(RUTA_SPRITES + nombreArchivo)) {
            if (is == null) {
                System.err.println("No se encontró el sprite: " + nombreArchivo);
                return null;
            }
            Image img = new Image(is);
            cache.put(nombreArchivo, img);
            return img;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Dimensiones getDimensionesPara(TipoEnte tipoEnte, Enum<?> subtipo) {
        ClaveSprite clave = new ClaveSprite(tipoEnte, subtipo);
        SpriteConfig config = configs.getOrDefault(clave, new SpriteConfig("default", 1));
        String nombreArchivo = (config.frames > 1) ? config.nombreBase + "0_20x20.png" : config.nombreBase + ".png";
        Image img = obtenerSprite(nombreArchivo);
        if (img != null) {
            return new Dimensiones((int) img.getWidth(), (int) img.getHeight());
        }
        return new Dimensiones(20, 20);
    }
}
