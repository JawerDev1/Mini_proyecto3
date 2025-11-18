package controller;

import model.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Controlador que contiene la logica de combate y turno.
 * La vista llama a estos metodos y muestra los Strings que devuelve.
 */
public class ControladorJuego {

    private ArrayList<Jugador> heroes;
    private ArrayList<Enemigo> enemigos;
    private Random random = new Random();

    public ControladorJuego(ArrayList<Jugador> heroes, ArrayList<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
    }

    public ArrayList<Jugador> getHeroes() { return heroes; }
    public ArrayList<Enemigo> getEnemigos() { return enemigos; }

    /**
     * Ejecuta la accion de un heroe.
     * usa los metodos de model que devuelven Strings (no imprime aqui).
     */
    public String accionHeroe(int indiceHeroe, boolean esHabilidad, int indiceObjetivo) {
        StringBuilder resultado = new StringBuilder();

        if (indiceHeroe < 0 || indiceHeroe >= heroes.size()) {
            return "Indice de heroe invalido.\n";
        }
        if (indiceObjetivo < 0 || indiceObjetivo >= enemigos.size()) {
            return "Indice de enemigo invalido.\n";
        }

        Jugador heroe = heroes.get(indiceHeroe);
        Enemigo enemigo = enemigos.get(indiceObjetivo);

        if (!heroe.estaVivo()) {
            return heroe.getNombre() + " esta muerto y no puede actuar.\n";
        }

        // Si heroe dormido -> reducir turnos y no actuar
        if (heroe.getEstado() == Estado.DORMIDO) {
            heroe.reducirTurnosEstado();
            return heroe.getNombre() + " sigue dormido y pierde el turno.\n";
        }

        // Ejecutar la accion solicitada y recoger el mensaje devuelto
        String accionMsg;
        if (esHabilidad) {
            accionMsg = heroe.usarHabilidadEspecial(enemigo);
        } else {
            accionMsg = heroe.ataqueNormal(enemigo);
        }
        resultado.append(accionMsg);

        // Si el enemigo ha muerto tras la accion -> eliminarlo de la lista
        if (!enemigo.estaVivo()) {
            resultado.append(enemigo.getNombre()).append(" ha sido derrotado.\n");
            enemigos.remove(enemigo);
        }

        return resultado.toString();
    }

    /**
     * Turno de los enemigos. Devuelve el log como String.
     */
    public String turnoEnemigos() {
        StringBuilder resultado = new StringBuilder();

        for (Enemigo e : new ArrayList<>(enemigos)) {
            if (!e.estaVivo()) continue;

            // Si enemigo dormido -> reducir turnos y saltar
            if (e.getEstado() == Estado.DORMIDO) {
                e.reducirTurnosEstado();
                resultado.append(e.getNombre()).append(" sigue dormido y pierde el turno.\n");
                continue;
            }

            // Elegir heroe vivo al azar
            Jugador objetivo = obtenerHeroeVivoAleatorio();
            if (objetivo == null) break;

            // Elegir aleatoriamente entre ataque normal o especial
            boolean usarEspecial = random.nextBoolean();
            String accionMsg = "";
            if (usarEspecial) {
                switch (e.getTipoEnemigo()) {
                    case SLIME -> accionMsg = e.ataqueSlime(objetivo);
                    case DRACKY -> accionMsg = e.picotazo(objetivo);
                    case PATYPUNK -> accionMsg = e.golpeGarrote(objetivo);
                    case SPIKED_HARE -> accionMsg = e.patadaGiratoria(objetivo);
                    case TERROR_TABBY -> accionMsg = e.sleepAttack(objetivo);
                }
            } else {
                accionMsg = e.ataqueNormal(objetivo);
            }

            resultado.append(accionMsg);

            if (!objetivo.estaVivo()) {
                resultado.append(objetivo.getNombre()).append(" ha sido derrotado.\n");
            }

            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }

        return resultado.toString();
    }

    private Jugador obtenerHeroeVivoAleatorio() {
        ArrayList<Jugador> vivos = new ArrayList<>();
        for (Jugador h : heroes) if (h.estaVivo()) vivos.add(h);
        if (vivos.isEmpty()) return null;
        return vivos.get(random.nextInt(vivos.size()));
    }

    public boolean hayHeroesVivos() {
        for (Jugador h : heroes) if (h.estaVivo()) return true;
        return false;
    }

    public boolean hayEnemigosVivos() {
        for (Enemigo e : enemigos) if (e.estaVivo()) return true;
        return false;
    }
}
