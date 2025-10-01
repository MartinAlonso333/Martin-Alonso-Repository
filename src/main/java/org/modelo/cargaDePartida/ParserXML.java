package org.modelo.cargaDePartida;

import org.modelo.Juego;
import org.modelo.entidades.Ente;
import org.modelo.entidades.RegistroEntidades;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Objects;

public class ParserXML {

    public static void cargarNivel(String nombreArchivo, Juego juego, RegistroEntidades registro) {
        try {
            File file = new File(Objects.requireNonNull(ParserXML.class.getResource("/levels/GeneratedLevels/" + nombreArchivo + ".xml")).toURI());
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            Element root = doc.getDocumentElement();

            procesarNodo(root, registro, juego);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar nivel: " + nombreArchivo);
        }
    }

    private static void procesarNodo(Element elem, RegistroEntidades registro, Juego juego) {
        String tag = elem.getTagName(); // player, enemy o staticObject
        Ente ente = null;

        if (tag.equals("player")) {
            ente = registro.crearJugador(elem);

        } else if (tag.equals("enemy")) {
            String tipo = elem.getAttribute("type");
            ente = registro.crearEnteConTipo(elem, tipo);

        } else if (tag.equals("staticObject")) {
            String tipo = elem.getAttribute("type");
            ente = registro.crearEnteConTipo(elem, tipo);
        }

        if (ente != null) {
            juego.agregarEnte(ente);
        }

        // procesar hijos recursivamente (para <players>, <enemies>, etc.)
        NodeList hijos = elem.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);
            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                procesarNodo((Element) hijo, registro, juego);
            }
        }
    }

}
