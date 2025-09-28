package juego.vista;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GestorSprites {

    private static final Map<String, Image> cache = new HashMap<>();
    private static final String RUTA_SPRITES = "/sprites/";

    /**
     * Devuelve el sprite correspondiente al nombre de archivo.
     * Si ya se cargó, lo devuelve del cache.
     */
    public static Image obtenerSprite(String nombreArchivo) {
        if (cache.containsKey(nombreArchivo)) {
            return cache.get(nombreArchivo);
        }

        try (InputStream is = GestorSprites.class.getResourceAsStream(RUTA_SPRITES + nombreArchivo)) {
            if (is == null) {
                System.err.println("No se encontró el sprite: " + nombreArchivo);
                return null;
            }
            Image img = new Image(is);
            cache.put(nombreArchivo, img);
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve las dimensiones del sprite como un arreglo [ancho, alto].
     */
    public static int[] obtenerDimensiones(String nombreArchivo) {
        Image img = obtenerSprite(nombreArchivo);
        if (img == null) return new int[]{32, 32};
        return new int[]{(int) img.getWidth(), (int) img.getHeight()};
    }
}