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

    private static final Map<String, SpriteConfig> configs = new HashMap<>();
    private static final Map<String, Image> cache = new HashMap<>();
    private static final String RUTA_SPRITES = "/sprites/";

    static {
        // --- BLOQUES ---
        configs.put("steelBlock", new SpriteConfig("SteelBlock", 1));
        configs.put("brickBlock", new SpriteConfig("BrickBlock", 1));
        configs.put("baseBlock", new SpriteConfig("BaseBlock", 1));
        configs.put("forestBlock", new SpriteConfig("ForestBlock", 1));
        configs.put("waterBlock", new SpriteConfig("WaterBlock", 1));

        // --- ENEMIGOS ---
        configs.put("regularEnemy", new SpriteConfig("EnemyTankRegular", 2));
        configs.put("enemyFast", new SpriteConfig("EnemyTankFast", 2));
        configs.put("enemyStrong", new SpriteConfig("EnemyTankPowerful", 2));
        configs.put("enemyArmored", new SpriteConfig("EnemyTankHeavy", 2));

        // --- JUGADOR ---
        configs.put("player", new SpriteConfig("PlayerTank", 2));

        // --- BALA ---
        configs.put("bullet", new SpriteConfig("Shot", 1));

        // --- POWERUPS ---
        configs.put("helmetPowerUp", new SpriteConfig("PowerUp-Helmet", 1));
        configs.put("starPowerUp", new SpriteConfig("PowerUp-Star", 1));
        configs.put("grenadePowerUp", new SpriteConfig("PowerUp-Grenade", 1));
    }

    public static Map<Direccion, List<Image>> getAnimacionesPara(String tipo) {
        Map<Direccion, List<Image>> animaciones = new EnumMap<>(Direccion.class);

        // jugadores: "player1", "player2", etc. → usar "player"
        String clave = tipo.startsWith("player") ? "player" : tipo;

        SpriteConfig config = configs.getOrDefault(clave, new SpriteConfig("default", 1));

        for (Direccion dir : Direccion.values()) {
            List<Image> frames = new ArrayList<>();
            if (config.frames > 1) {
                for (int i = 0; i < config.frames; i++) {
                    String nombre = config.nombreBase + i + ".png";
                    frames.add(obtenerSprite(nombre));
                }
            } else {
                frames.add(obtenerSprite(config.nombreBase + ".png"));
            }
            animaciones.put(dir, frames);
        }
        return animaciones;
    }

    public static Dimensiones getDimensionesPara(String tipo) {
        // jugadores: "player1", "player2", etc. → usar "player"
        String clave = tipo.startsWith("player") ? "player" : tipo;

        SpriteConfig config = configs.getOrDefault(clave, new SpriteConfig("default", 1));
        String nombreArchivo = (config.frames > 1)
                ? config.nombreBase + "0.png"
                : config.nombreBase + ".png";
        Image img = obtenerSprite(nombreArchivo);
        if (img != null) {
            return new Dimensiones((int) img.getWidth(), (int) img.getHeight());
        }
        return new Dimensiones(20, 20);
    }

    private static Image obtenerSprite(String nombreArchivo) {
        if (cache.containsKey(nombreArchivo)) return cache.get(nombreArchivo);
        String ruta = RUTA_SPRITES + nombreArchivo;
        try (InputStream is = GestorSprites.class.getResourceAsStream(ruta)) {
            if (is == null) {
                System.err.println("❌ No se encontró el sprite en: " + ruta);
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
}
