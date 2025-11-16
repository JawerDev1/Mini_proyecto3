package view;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

public class InterfazJuego extends JFrame {

    private JTextArea areaTexto;
    private JButton btnAtacar, btnHabilidad;
    private JComboBox<String> listaEnemigos;
    private ArrayList<Jugador> heroes;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<Enemigo> enemigosVivos = new ArrayList<>();
    private Random random = new Random();

    private int indiceHeroeActual = 0; // controla qué héroe debe actuar

    public InterfazJuego() {
        setTitle("⚔️ DRAGON QUEST - BATALLA ⚔️");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 230, 250));

        inicializarPersonajes();
        inicializarInterfaz();

        actualizarListaEnemigos();
        actualizarTurnoActual();
    }

    // ------------------------- Inicialización -------------------------
    private void inicializarPersonajes() {
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();

        // usa los datos que ya tenías
        heroes.add(new Jugador(TipoHeroe.HEROE, "Heroe", 45, 8, 8, 6, 6));
        heroes.add(new Jugador(TipoHeroe.YANGUS, "Yangus", 50, 10, 10, 8, 3));
        heroes.add(new Jugador(TipoHeroe.YESSICA, "Jessica", 40, 14, 7, 5, 7));
        heroes.add(new Jugador(TipoHeroe.ANGELO, "Angelo", 42, 12, 8, 6, 6));

        enemigos.add(new Enemigo(TipoEnemigo.SPIKED_HARE, "Spiked Hare", 30, 6, 6, 5, 7));
        enemigos.add(new Enemigo(TipoEnemigo.DRACKY, "Dracky", 35, 6, 7, 5, 8));
        enemigos.add(new Enemigo(TipoEnemigo.PATYPUNK, "PatyPunk", 40, 4, 9, 7, 5));
        enemigos.add(new Enemigo(TipoEnemigo.TERROR_TABBY, "Terror Tabby", 42, 8, 9, 6, 7));
    }

    private void inicializarInterfaz() {
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaTexto.setBackground(new Color(250, 250, 240));
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de batalla"));
        add(scroll, BorderLayout.CENTER);

        // Panel de acciones en un cuadro
        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new GridLayout(2, 2, 10, 10));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones disponibles"));
        panelAcciones.setBackground(new Color(240, 240, 255));

        btnAtacar = new JButton("⚔️ Atacar");
        btnHabilidad = new JButton("✨ Habilidad");
        listaEnemigos = new JComboBox<>();

        panelAcciones.add(new JLabel("🎯 Enemigo objetivo:", SwingConstants.CENTER));
        panelAcciones.add(listaEnemigos);
        panelAcciones.add(btnAtacar);
        panelAcciones.add(btnHabilidad);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelAcciones, BorderLayout.CENTER);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(panelInferior, BorderLayout.SOUTH);

        // acciones
        btnAtacar.addActionListener(e -> realizarAtaque(false));
        btnHabilidad.addActionListener(e -> realizarAtaque(true));
    }

    // ------------------------- Lógica del turno -------------------------
    private void realizarAtaque(boolean esHabilidad) {
        // verificar enemigo seleccionado
        if (listaEnemigos.getSelectedIndex() == -1) {
            areaTexto.append("Selecciona un enemigo antes de atacar.\n");
            return;
        }

        // obtener héroe y enemigo objetivo
        if (indiceHeroeActual < 0 || indiceHeroeActual >= heroes.size()) {
            areaTexto.append("Error: índice de héroe inválido.\n");
            return;
        }

        Jugador heroe = heroes.get(indiceHeroeActual);
        Enemigo objetivo = enemigosVivos.get(listaEnemigos.getSelectedIndex());

        // 1) Si héroe está muerto, saltar
        if (!heroe.estaVivo()) {
            areaTexto.append(heroe.getNombre() + " está KO, salta su turno.\n");
            siguienteTurno();
            return;
        }

        // 2) Si héroe está dormido: 50% de chance de despertar
        if (heroe.getEstado() == Estado.DORMIDO) {
            double prob = Math.random();
            if (prob <= 0.5) {
                // despierta y actúa este turno
                heroe.setEstado(Estado.NORMAL, 0);
                areaTexto.append(heroe.getNombre() + " se despierta y puede actuar.\n");
                // (seguimos para que actúe)
            } else {
                // sigue dormido y pierde turno; reducimos duración
                areaTexto.append(heroe.getNombre() + " sigue dormido y pierde el turno.\n");
                heroe.reducirTurnosEstado();
                siguienteTurno();
                return;
            }
        }

        // Guardar HP/MP antes para mostrar cambios
        int hpAntesEnemigo = objetivo.getHp();
        int hpAntesHeroe = heroe.getHp();
        int mpAntesHeroe = heroe.getMp();

        // 3) Ejecutar acción: habilidad o ataque normal
        if (esHabilidad) {
            // Implementación de habilidad según tipo de héroe (sin depender de métodos externos)
            switch (heroe instanceof Jugador ? ((Jugador) heroe).getTipoHeroe() : TipoHeroe.HEROE) {
                case HEROE -> {
                    // cura 12, consume 3 MP
                    if (heroe.getMp() >= 3) {
                        heroe.gastarMp(3);
                        heroe.curar(12);
                        areaTexto.append(heroe.getNombre() + " usa Cura (+12 HP).\n");
                    } else {
                        areaTexto.append(heroe.getNombre() + " no tiene MP para usar Cura. Realiza ataque normal\n");
                        heroe.atacar(objetivo);
                    }
                }
                case ANGELO -> {
                    if (heroe.getMp() >= 4) {
                        heroe.gastarMp(4);
                        heroe.curar(15);
                        areaTexto.append(heroe.getNombre() + " usa Cura avanzada (+15 HP).\n");
                    } else {
                        areaTexto.append(heroe.getNombre() + " no tiene MP para usar Cura avanzada. Realiza ataque normal\n");
                        heroe.atacar(objetivo);
                    }
                }
                case YESSICA -> {
                    if (heroe.getMp() >= 4) {
                        heroe.gastarMp(4);
                        int danio = random.nextInt(6) + 10; // 10-15
                        objetivo.recibirDanio(danio);
                        areaTexto.append(heroe.getNombre() + " lanza Frizz e inflige " + danio + " de daño mágico.\n");
                    } else {
                        areaTexto.append(heroe.getNombre() + " no tiene MP para lanzar Frizz. Realiza ataque normal\n");
                        heroe.atacar(objetivo);
                    }
                }
                case YANGUS -> {
                    // golpe poderoso: 30% de éxito, 50% más daño
                    int prob = random.nextInt(100) + 1;
                    if (prob <= 30) {
                        int danioBase = Math.max(1, heroe.getAtaque() - objetivo.getDefensa());
                        int danioTotal = (int) (danioBase * 1.5);
                        objetivo.recibirDanio(danioTotal);
                        areaTexto.append(heroe.getNombre() + " usa Golpe Poderoso e inflige " + danioTotal + " de daño.\n");
                    } else {
                        areaTexto.append(heroe.getNombre() + " intenta Golpe Poderoso y falla.\n");
                    }
                }
                default -> heroe.atacar(objetivo);
            }
        } else {
            // ataque normal (usa el método de Personaje)
            heroe.atacar(objetivo);
            areaTexto.append(heroe.getNombre() + " ataca a " + objetivo.getNombre() + ".\n");
        }

        // 4) Mostrar diferencias de HP/MP
        int hpDespuesEnemigo = objetivo.getHp();
        int danoInfligido = hpAntesEnemigo - hpDespuesEnemigo;
        if (danoInfligido > 0) {
            areaTexto.append(objetivo.getNombre() + " recibió " + danoInfligido + " de daño (HP restante: " + Math.max(hpDespuesEnemigo, 0) + ").\n");
        }

        int hpDespuesHeroe = heroe.getHp();
        int curacion = hpDespuesHeroe - hpAntesHeroe;
        if (curacion > 0) {
            areaTexto.append(heroe.getNombre() + " recuperó " + curacion + " HP (HP actual: " + hpDespuesHeroe + ").\n");
        }

        int mpDespuesHeroe = heroe.getMp();
        int mpGastado = mpAntesHeroe - mpDespuesHeroe;
        if (mpGastado > 0) {
            areaTexto.append(heroe.getNombre() + " gastó " + mpGastado + " MP (MP restante: " + mpDespuesHeroe + ").\n");
        }

        // 5) Si enemigo muere
        if (!objetivo.estaVivo()) {
            areaTexto.append(objetivo.getNombre() + " ha sido derrotado.\n");
        }

        // actualizar combo de enemigos vivos
        actualizarListaEnemigos();

        // 6) comprobar fin de batalla
        if (!hayVivos(enemigos)) {
            finDeBatalla();
            return;
        }

        // 7) pasar al siguiente turno automáticamente
        siguienteTurno();
    }

    private void siguienteTurno() {
        // Avanzar al siguiente héroe vivo. Si todos actuaron -> turno enemigos.
        int intentos = 0;
        do {
            indiceHeroeActual++;
            if (indiceHeroeActual >= heroes.size()) {
                // turno de enemigos
                turnoEnemigos();
                indiceHeroeActual = 0;
                break;
            }
            intentos++;
            // si todos están muertos, romper
            if (intentos > heroes.size()) break;
        } while (!heroes.get(indiceHeroeActual).estaVivo());

        // Si después del turno enemigo no hay héroes vivos => fin
        if (!hayVivos(heroes)) {
            finDeBatalla();
            return;
        }

        // Mostrar el turno actual (si el héroe está vivo)
        if (heroes.get(indiceHeroeActual).estaVivo()) {
            actualizarTurnoActual();
        } else {
            // si héroe actual está muerto, avanzar recursivamente
            siguienteTurno();
        }
    }

    private void turnoEnemigos() {
        areaTexto.append("\nTurno de los enemigos\n");

        for (Enemigo e : enemigos) {
            if (!e.estaVivo()) continue;

            // Si enemigo está dormido: 50% chance de despertarse
            if (e.getEstado() == Estado.DORMIDO) {
                double prob = Math.random();
                if (prob <= 0.5) {
                    e.setEstado(Estado.NORMAL, 0);
                    areaTexto.append("💤 " + e.getNombre() + " se despierta y puede actuar.\n");
                } else {
                    areaTexto.append("💤 " + e.getNombre() + " sigue dormido y pierde el turno.\n");
                    e.reducirTurnosEstado();
                    continue;
                }
            }

            Jugador objetivo = obtenerHeroeVivoAleatorio();
            if (objetivo == null) break;

            int hpAntes = objetivo.getHp();

            boolean usarEspecial = random.nextBoolean();
            if (usarEspecial) {
                switch (e.getTipoEnemigo()) {
                    case SLIME -> e.ataqueSlime(objetivo);
                    case DRACKY -> e.picotazo(objetivo);
                    case PATYPUNK -> e.golpeGarrote(objetivo);
                    case SPIKED_HARE -> e.patadaGiratoria(objetivo);
                    case TERROR_TABBY -> e.sleepAttack(objetivo);
                }
                areaTexto.append(e.getNombre() + " usa su habilidad sobre " + objetivo.getNombre() + ".\n");
            } else {
                e.ataqueNormal(objetivo);
                areaTexto.append(e.getNombre() + " ataca a " + objetivo.getNombre() + ".\n");
            }

            int dano = hpAntes - objetivo.getHp();
            if (dano > 0) {
                areaTexto.append(objetivo.getNombre() + " recibe " + dano + " de daño (HP restante: " + Math.max(objetivo.getHp(), 0) + ").\n");
            }

            if (!objetivo.estaVivo()) {
                areaTexto.append(objetivo.getNombre() + " ha sido derrotado.\n");
            }

            // pequeña pausa visual (no bloqueante en la GUI principal)
            try { Thread.sleep(400); } catch (InterruptedException ignored) {}
        }

        // actualizar lista de enemigos vivos en GUI
        actualizarListaEnemigos();
    }

    // ------------------------- Métodos auxiliares -------------------------
    private boolean hayVivos(ArrayList<? extends Personaje> lista) {
        for (Personaje p : lista) if (p.estaVivo()) return true;
        return false;
    }

    private Jugador obtenerHeroeVivoAleatorio() {
        ArrayList<Jugador> vivos = new ArrayList<>();
        for (Jugador h : heroes) if (h.estaVivo()) vivos.add(h);
        if (vivos.isEmpty()) return null;
        return vivos.get(random.nextInt(vivos.size()));
    }

    private void actualizarListaEnemigos() {
        listaEnemigos.removeAllItems();
        enemigosVivos.clear();
        for (Enemigo e : enemigos) {
            if (e.estaVivo()) {
                enemigosVivos.add(e);
                listaEnemigos.addItem(e.getNombre() + " (HP: " + e.getHp() + ")");
            }
        }
        // Si ya no hay enemigos vivos, deshabilitar selección
        boolean hay = !enemigosVivos.isEmpty();
        listaEnemigos.setEnabled(hay);
    }

    private void actualizarTurnoActual() {
        Jugador heroe = heroes.get(indiceHeroeActual);
        areaTexto.append("\nTurno de " + heroe.getNombre() + " (HP: " + heroe.getHp() + " MP: " + heroe.getMp() + ")\n");
    }

    private void finDeBatalla() {
        areaTexto.append("\n=============================\n");
        if (hayVivos(heroes)) {
            areaTexto.append("¡Los héroes han ganado la batalla!\n");
        } else {
            areaTexto.append("Los enemigos han triunfado...\n");
        }
        areaTexto.append("=============================\n");
        btnAtacar.setEnabled(false);
        btnHabilidad.setEnabled(false);
        listaEnemigos.setEnabled(false);

        // Detener la musica
        AudioPlayer.getInstance().stop();
    }
}