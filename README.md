# Trabajo Práctico 1

## Universidad
Universidad de Buenos Aires

## Facultad
Facultad de Ingeniería 

## Materia
Paradigmas de Programación

---

## Docentes:
---Profesor: Diego Essaya

---Jefe de Trabajos Prácticos: Santiago Maraggi

---Ayudantes:

-Alberto Carmona-

-Dante Finci-

-José Ignacio Castro Martínez-

-Lihuén Carranza-

-Luca Salluzzi-

-María Macarena Vita Sanchez-

### Docente corrector:

---Ignacio Castro

## Integrantes del grupo:
- Franco Martin Alonso
- Martin Ariel Garcia

### Nombre del grupo:
**martin-al-cuadrado**

---

## Nombre del Proyecto
**YABC - Yet Another Battle City**

---

## Descripción Breve
Este proyecto consiste en el desarrollo de una versión propia del clásico videojuego **Battle City**, implementado en Java.  
El objetivo es recrear la jugabilidad original incorporando la lógica de tanques, enemigos, obstáculos y power-ups, aplicando los conceptos vistos en la materia sobre programación orientada a objetos y estructuras de datos.

---

## Enlace a Videos de presentacion:
https://drive.google.com/drive/folders/1UhLCeC9DDqd_ljM_xXyzODiH8SU1HSah?usp=drive_link

---

## Instrucciones de ejecución:
**Requisitos:**
Tener JDK version 17 instalado. Maven instalado (de ser posible actualizado a las ultimas versiones) y en el path del sistema.

**Pasos:**

Clonar el repositorio con 'git clone git@github.com:paradigmas-tb025-essaya/tp1-martin-al-cuadrado.git', entrar a la carpeta 'tp1-martin-al-cuadrado' con el comando cd.


Opción 1) En la terminal, en la raiz del proyecto, ejectuar 'mvn clean compile', luego 'mvn exec:java -Dexec.mainClass="org.JuegoApp"'. Luego de esto se deberia mostrar la ventana del juego.


Opción 2) Desde el IDE Intellij, asegurarse en 'file->Project Structure', SDK esté en la version 'ms-17'. Ir a la ventana de Maven a la derecha, entrar a la carpeta 'juego-javafx/Plugins/clean/' y ejecutar clean:clean, luego en 'juego-javafx/Plugins/compiler/' ejecutar 'compiler:compile', y por ultimo entrar a 'juego-javafx/Plugins/javafx' y ejecutar 'javafx:run'. Luego de esto se deberia mostrar la ventana del juego.


## Instrucciones de juego (comandos):

Controles (movimiento + DISPARO):

---Jugador 1: W-A-S-D + ESPACIO

---Jugador 2: flechas del teclado + ENTER




