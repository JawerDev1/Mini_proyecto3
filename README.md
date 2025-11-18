# 🐉 Mini Proyecto 3 - Dragon Quest  
### Universidad del Valle  
**Asignatura:** Programación Orientada a Eventos  
**Lenguaje:** Java (POO + Swing + MVC)  

---

## 👥 Integrantes del grupo
| Nombre                         | Rol / Aporte principal                                   | Código       |
|--------------------------------|----------------------------------------------------------|--------------|
| **Kevin Andrés Rosero Romo**   | Lógica base del combate y desarrollo de clases principales | 2459554-2724 |
| **Jhon Jawer Cuero Gómez**     | Interfaz gráfica y sistema de sonido | 2459544-2724 |


---
## 🧩 Descripción general del proyecto

Este proyecto implementa un sistema de combate RPG inspirado en Dragon Quest, usando el patrón MVC (Modelo–Vista–Controlador).
El usuario controla varios héroes que enfrentan enemigos por turnos.
La interfaz gráfica permite elegir acciones como atacar o usar habilidades, mientras el controlador gestiona la batalla y actualiza la vista.

Incluye además un sistema de sonido que reproduce música y efectos durante el combate.

---

## 🧱 Arquitectura del Proyecto (Patrón MVC)

La estructura del proyecto se organizó siguiendo el patrón MVC.
Tu aporte estuvo principalmente en View, Controller y utils.

### 🔵 Modelo (Model)

Contiene las clases que representan la lógica y los datos del combate.

| Archivo |	Descripción |
|----------|----------------|
| **Personaje.java** |	Clase base para héroes y enemigos. |
| **Jugador.java**	 | Representa a cada héroe. |
| **Enemigo.java** |	Representa a los enemigos. |
| **TipoHeroe.java, TipoEnemigo.java** |	Enumeraciones  para clasificar personajes. |

---

### 🟣 Vista (View)

Clases que muestran la información al usuario y reciben eventos de interacción.

Archivo	Descripción
InterfazJuego.java	Ventana principal del juego. Muestra el combate, lista enemigos, botones de acción, logs, etc.

Características implementadas:
✓ Interfaz completa con Swing
✓ Área de texto para logs
✓ Botones de acciones (Atacar, Habilidad)
✓ ComboBox para seleccionar enemigos
✓ Diseño visual (colores, bordes, paneles)
✓ Preparación para agregar imágenes de fondo

---

### 🟠 Controlador (Controller)

Gestiona la lógica entre vista y modelo.

Archivo	Descripción
ControladorJuego.java	Recibe interacciones desde la vista, ejecuta la lógica del modelo y retorna los resultados para mostrar en pantalla.

Funciones del controlador:

Ejecutar ataques

Verificar enemigos vivos

Controlar turnos

Retornar mensajes para la vista

Comunicar vista ↔ modelo

---

### 🟡 Utilidades (utils)

Clases auxiliares.

| Archivo	 | Descripción |
|---------------|------------|
| **AudioPlayer.java** |	Reproduce música de fondo y efectos de sonido. |
| **Carpeta music/**	| Donde guardaste los archivos .wav utilizados en el juego. |

---

### 🎮 Funcionalidades principales

✔ Sistema de combate por turnos

✔ Gestión de héroes y enemigos

✔ Interfaz gráfica responsiva

✔ Reproducción de música durante la batalla

✔ Botones de acción (Atacar / Habilidad)

✔ Log detallado del combate

✔ Selección dinámica de enemigos vivos

✔ Fin de batalla con música detenida

---

### 🧰 Tecnologías utilizadas

Java 17+

Swing (GUI)

POO (Herencia, Polimorfismo, Encapsulamiento)

MVC (Modelo–Vista–Controlador)

javax.sound.sampled (Audio)

Git / GitHub (trabajo colaborativo por rama
