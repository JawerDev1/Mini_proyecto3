package view;

import controller.ControladorJuego;
import model.*;
import utils.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Vista: muestra mensajes que el controlador devuelve.
 * No usa emojis ni tildes.
 */
public class InterfazJuego extends JFrame {

    private JTextArea areaTexto;
    private JButton btnAtacar, btnHabilidad;
    private JComboBox<String> listaEnemigos;
    private ControladorJuego controlador;

    private ArrayList<Jugador> heroes;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<Enemigo> enemigosVivos = new ArrayList<>();
    private Random random = new Random();

    private int indiceHeroeActual = 0;

    public InterfazJuego() {
        setTitle("DRAGON QUEST - BATALLA");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 230, 250));

        inicializarDatos();
        inicializarInterfaz();

        actualizarListaEnemigos();
        actualizarTurnoActual();
    }

    private void inicializarDatos() {
        heroes = new ArrayList<>();
        enemigos = new ArrayList<>();

        heroes.add(new Jugador(TipoHeroe.HEROE, "Heroe", 45, 8, 8, 6, 6));
        heroes.add(new Jugador(TipoHeroe.YANGUS, "Yangus", 50, 10, 10, 8, 3));
        heroes.add(new Jugador(TipoHeroe.YESSICA, "Jessica", 40, 14, 7, 5, 7));
        heroes.add(new Jugador(TipoHeroe.ANGELO, "Angelo", 42, 12, 8, 6, 6));

        enemigos.add(new Enemigo(TipoEnemigo.SPIKED_HARE, "Spiked Hare", 30, 6, 6, 5, 7));
        enemigos.add(new Enemigo(TipoEnemigo.DRACKY, "Dracky", 35, 6, 7, 5, 8));
        enemigos.add(new Enemigo(TipoEnemigo.PATYPUNK, "PatyPunk", 40, 4, 9, 7, 5));
        enemigos.add(new Enemigo(TipoEnemigo.TERROR_TABBY, "Terror Tabby", 42, 8, 9, 6, 7));

        controlador = new ControladorJuego(heroes, enemigos);
    }

    private void inicializarInterfaz() {
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaTexto.setBackground(new Color(250, 250, 240));
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de batalla"));
        add(scroll, BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new GridLayout(2, 2, 10, 10));
        panelAcciones.setBorder(BorderFactory.createTitledBorder("Acciones disponibles"));
        panelAcciones.setBackground(new Color(240, 240, 255));

        btnAtacar = new JButton("Atacar");
        btnHabilidad = new JButton("Habilidad");
        listaEnemigos = new JComboBox<>();

        panelAcciones.add(new JLabel("Enemigo objetivo:", SwingConstants.CENTER));
        panelAcciones.add(listaEnemigos);
        panelAcciones.add(btnAtacar);
        panelAcciones.add(btnHabilidad);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelAcciones, BorderLayout.CENTER);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(panelInferior, BorderLayout.SOUTH);

        btnAtacar.addActionListener(e -> realizarAtaque(false));
        btnHabilidad.addActionListener(e -> realizarAtaque(true));
    }

    private void realizarAtaque(boolean esHabilidad) {
        if (listaEnemigos.getSelectedIndex() == -1) {
            areaTexto.append("Selecciona un enemigo antes de atacar.\n");
            return;
        }

        if (indiceHeroeActual < 0 || indiceHeroeActual >= heroes.size()) {
            areaTexto.append("Indice de heroe invalido.\n");
            return;
        }

        Jugador heroe = heroes.get(indiceHeroeActual);
        Enemigo objetivo = enemigosVivos.get(listaEnemigos.getSelectedIndex());

        String resultado = controlador.accionHeroe(indiceHeroeActual, esHabilidad, enemigos.indexOf(objetivo));
        areaTexto.append(resultado);

        actualizarListaEnemigos();

        if (!controlador.hayEnemigosVivos()) {
            finDeBatalla();
            return;
        }

        siguienteTurno();
    }

    private void siguienteTurno() {
        int intentos = 0;
        do {
            indiceHeroeActual++;
            if (indiceHeroeActual >= heroes.size()) {
                areaTexto.append("\n--- Turno de los enemigos ---\n");
                String res = controlador.turnoEnemigos();
                areaTexto.append(res);
                actualizarListaEnemigos();
                indiceHeroeActual = 0;
                break;
            }
            intentos++;
            if (intentos > heroes.size()) break;
        } while (!heroes.get(indiceHeroeActual).estaVivo());

        if (!controlador.hayHeroesVivos()) {
            finDeBatalla();
            return;
        }

        if (heroes.get(indiceHeroeActual).estaVivo()) {
            actualizarTurnoActual();
        } else {
            siguienteTurno();
        }
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
        listaEnemigos.setEnabled(!enemigosVivos.isEmpty());
    }

    private void actualizarTurnoActual() {
        Jugador heroe = heroes.get(indiceHeroeActual);
        areaTexto.append("\nTurno de " + heroe.getNombre() + " (HP: " + heroe.getHp() + " MP: " + heroe.getMp() + ")\n");
    }

    private void finDeBatalla() {
        areaTexto.append("\n=============================\n");
        if (controlador.hayHeroesVivos()) {
            areaTexto.append("Los heroes han ganado la batalla!\n");
        } else {
            areaTexto.append("Los enemigos han triunfado...\n");
        }
        areaTexto.append("=============================\n");
        btnAtacar.setEnabled(false);
        btnHabilidad.setEnabled(false);
        listaEnemigos.setEnabled(false);

        AudioPlayer.getInstance().stop();
    }
}
