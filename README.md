🐉 DRAGON QUEST – Módulo de Interfaz, Controlador y Audio
Rama: ramajhon

Este README documenta las funcionalidades desarrolladas en la rama ramajhon, correspondientes a mi aporte dentro del proyecto Dragon Quest (Java – Swing – MVC).

📌 Aportes realizados en esta rama
✔ 1. Implementación de las vistas (paquete view)

Se desarrollaron las clases:

MenuPrincipal.java

Ventana inicial del juego.

Botones:

Iniciar Batalla

Créditos

Salir

Estilización con Swing.

Vinculación con AudioPlayer para iniciar música.

Navegación hacia InterfazJuego.

InterfazJuego.java

Ventana principal de combate.

Componentes implementados:

Área de texto para registro de batalla.

Botones: Atacar y Habilidad.

ComboBox para seleccionar enemigos.

Inicialización de héroes y enemigos.

Mostrado del turno actual.

Integración con el controlador de la lógica de batalla.

✔ 2. Módulo de Audio (carpeta utils/)

Se creó:

AudioPlayer.java

Implementado con patrón Singleton.

Reproducción continua (loop) de música.

Control de audio para:

Reproducir

Detener

Loop de batalla

Integración con MenuPrincipal e InterfazJuego.

Además, se añadió:

utils/music/musica_batalla.wav

✔ 3. Controlador de la lógica de batalla

Aunque la lógica principal está dentro de InterfazJuego, se agregó/organizó:

Manejo del flujo de turnos.

Ataques normales y habilidades.

Verificación de HP/MP antes y después de cada acción.

Eliminación de enemigos derrotados.

Control de estados (dormido, normal).

Condiciones de victoria o derrota.

Avance automático entre turnos.

📂 Estructura aportada en esta rama
src/
 ├── view/
 │    ├── MenuPrincipal.java
 │    └── InterfazJuego.java
 │
 ├── utils/
 │    ├── AudioPlayer.java
 │    └── music/
 │         └── musica_batalla.wav
 │
 └── controller/
      └── (flujo de batalla integrado en las vistas)

▶ Ejecutar el módulo

Para probar las vistas y audio implementados:

Abrir el proyecto en tu IDE.

Ejecutar:

MenuPrincipal.java

👤 Autor de esta rama

Jhon Jawer Cuero Gómez
Rama: rama-jhon