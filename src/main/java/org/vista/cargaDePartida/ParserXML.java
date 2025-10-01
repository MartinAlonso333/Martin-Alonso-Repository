package org.vista.cargaDePartida;

import org.modelo.Juego;
import org.modelo.entidades.Ente;
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
        // Solo procesar elementos que tengan atributo "type"
        if (elem.hasAttribute("type")) {
            try {
                Ente ente = registro.crearEnte(elem);
                if (ente != null) {
                    juego.agregarEnte(ente);
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Tipo de ente desconocido: " + elem.getAttribute("type"));
            }
        }

        // Procesar hijos recursivamente
        NodeList hijos = elem.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node hijo = hijos.item(i);
            if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                procesarNodo((Element) hijo, registro, juego);
            }
        }
    }
}
