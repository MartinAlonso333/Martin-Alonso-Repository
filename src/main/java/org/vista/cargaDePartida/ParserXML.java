package org.vista.cargaDePartida;

import org.modelo.entidades.Ente;
import org.modelo.entidades.TipoEnte;
import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.utilidades.Coordenada;
import org.modelo.utilidades.Direccion;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.InputStream;


public class ParserXML {

    public record ParEnteTipo(Ente ente, String tipoStr) {}


    public static List<ParEnteTipo> cargarNivel(int numJugadores, String pathXml) {
        List<ParEnteTipo> entesCargados = new ArrayList<>();
        Document doc = null;

        try {
            InputStream xmlStream = ParserXML.class.getResourceAsStream("/" + pathXml);
            if (xmlStream == null) {
                throw new IllegalArgumentException("Archivo XML no encontrado en resources: /" + pathXml +
                        "\nVerifica que esté en src/main/resources/" + pathXml +
                        "\nPath actual buscado: " + ParserXML.class.getResource("/" + pathXml));
            }
            System.out.println("XML cargado exitosamente desde resources: " + pathXml);  // Log para debug

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = builder.parse(new InputSource(xmlStream));
            xmlStream.close();
            doc.getDocumentElement().normalize();

            // --- Leer coordenadas de jugadores ---
            NodeList players = doc.getElementsByTagName("player");
            List<Coordenada> posicionesJugadores = new ArrayList<>();
            List<Direccion> direccionesJugadores = new ArrayList<>();

            for (int i = 0; i < players.getLength(); i++) {
                Element p = (Element) players.item(i);
                Coordenada pos = new Coordenada(
                        Integer.parseInt(p.getAttribute("x")),
                        Integer.parseInt(p.getAttribute("y"))
                );
                Direccion dir = p.hasAttribute("direccion") ?
                        Direccion.valueOf(p.getAttribute("direccion")) :
                        Direccion.ARRIBA;

                posicionesJugadores.add(pos);
                direccionesJugadores.add(dir);
            }

            // Crear solo la cantidad de jugadores solicitada y recolectar con tipoStr
            for (int i = 0; i < numJugadores && i < posicionesJugadores.size(); i++) {
                String tipoStr = "player" + (i + 1);  // "player1", "player2", etc.
                Coordenada pos = posicionesJugadores.get(i);
                Direccion dir = direccionesJugadores.get(i);
                // Crear jugador con ID (i+1)
                TanqueJugador jugador = RegistroEntidades.crearJugador(i + 1, pos, dir);
                entesCargados.add(new ParEnteTipo(jugador, tipoStr));
                System.out.println("Jugador " + tipoStr + " cargado en: " + pos);  // Log debug
            }

            // --- Enemigos ---
            NodeList enemies = doc.getElementsByTagName("enemy");
            for (int i = 0; i < enemies.getLength(); i++) {
                Element e = (Element) enemies.item(i);
                Coordenada pos = new Coordenada(
                        Integer.parseInt(e.getAttribute("x")),
                        Integer.parseInt(e.getAttribute("y"))
                );
                Direccion dir = e.hasAttribute("direccion") ?
                        Direccion.valueOf(e.getAttribute("direccion")) :
                        Direccion.ARRIBA;

                String typeStr = e.getAttribute("type");  // ej: "basicEnemy"
                if (typeStr.isEmpty()) {
                    System.err.println("Enemy sin 'type' en XML; saltando.");
                    continue;
                }
                Ente enemigo = RegistroEntidades.crearEntidad(typeStr, pos, dir);
                entesCargados.add(new ParEnteTipo(enemigo, typeStr));
                System.out.println("Enemigo " + typeStr + " cargado en: " + pos);  // Log debug
            }

            // --- Bloques y objetos estáticos ---
            NodeList staticObjects = doc.getElementsByTagName("staticObject");
            for (int i = 0; i < staticObjects.getLength(); i++) {
                Element b = (Element) staticObjects.item(i);
                Coordenada pos = new Coordenada(
                        Integer.parseInt(b.getAttribute("x")),
                        Integer.parseInt(b.getAttribute("y"))
                );

                String typeStr = b.getAttribute("type");  // ej: "brickBlock"
                if (typeStr.isEmpty()) {
                    System.err.println("StaticObject sin 'type' en XML; saltando.");
                    continue;
                }
                Ente bloque = RegistroEntidades.crearEntidad(typeStr, pos, Direccion.ARRIBA);
                entesCargados.add(new ParEnteTipo(bloque, typeStr));
                System.out.println("Bloque " + typeStr + " cargado en: " + pos);  // Log debug
            }

            System.out.println("Nivel cargado: " + entesCargados.size() + " entidades.");  // Log final

        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error al cargar nivel desde resources: " + pathXml);
            // Retorna lista vacía para no crash, pero log el error
        }
        return entesCargados;
    }
}


