🐉 DRAGON QUEST – Módulo MVC

Rama: rama-kevin


Aportes realizados en esta rama
 1. Implementación completa del Modelo 

En esta rama desarrollé y estructuré todo el núcleo lógico del juego, creando las clases que representan personajes, estados y tipos.

Clases implementadas:
Personaje.java

Clase base para todos los personajes del juego.

Contiene:

Vida, ataque y defensa

Métodos de daño

Control de estados

Métodos comunes para jugador/enemigo

Estado.java

Manejo de:

HP máximo y actual

MP 

Ataque

Defensa

Estados especiales

Jugador.java

Clase del héroe controlado por el jugador.

Integra atributos según el tipo de héroe.

Enemigo.java

Representación de enemigos del juego.

Atributos según dificultad o tipo.

Enums creados:

TipoPersonaje.java

TipoHeroe.java

TipoEnemigo.java

Estas enumeraciones permiten categorizar personajes y controlar su comportamiento.

 2. Controlador principal del juego (paquete controller/)

Creé el controlador encargado de manejar el flujo del juego bajo el patrón MVC:

ControladorJuego.java

Funciones implementadas:

Creación del héroe y enemigos.

Gestión completa de turnos.

Validación de ataques.

Ejecución de habilidades.

Envío de información a la vista.

Verificación de estados del combate:

Enemigos derrotados

Jugador sin HP

Victoria final

Coordinación total entre Modelo y Vista.

El controlador mantiene el juego ordenado y sin mezclar responsabilidades.
