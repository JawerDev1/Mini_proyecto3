package controller;

import java.util.ArrayList;
import java.util.Random;

public class ControladorJuego {

    private ArrayList<Personaje> heroes;
    private ArrayList<Personaje> enemigos;
    private int turnoActual = 0;

    public ControladorJuego(ArrayList<Personaje> heroes, ArrayList<Personaje> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
    }

    public ArrayList<Personaje> getHeroes() {
        return heroes;
    }

    public ArrayList<Personaje> getEnemigos() {
        return enemigos;
    }

    // Ejecuta la acción del héroe (1 = atacar, 2 = habilidad)
    public String accionHeroe(int indiceHeroe, int tipoAccion, int indiceObjetivo){
        Personaje heroe = heroes.get(indiceHeroe);
        Personaje enemigo = enemigos.get(indiceObjetivo);

        StringBuilder resultado = new StringBuilder();

        if (tipoAccion == 1) {
            resultado.append(heroe.getNombre()).append(" ataca a ").append(enemigo.getNombre()).append("...\n");
            heroe.atacar(enemigo);
        } else if (tipoAccion == 2) {
            resultado.append(heroe.getNombre()).append(" intenta usar una habilidad especial...\n");
        }

        if(!enemigo.estaVivo()){
            resultado.append(enemigo.getNombre()).append(" ha sido derrotado.\n");
            enemigos.remove(indiceObjetivo);
        }

        return resultado.toString();
    }

    // Turno de los enemigos
    public String turnoEnemigos(){
        StringBuilder resultado = new StringBuilder();
        Random rand = new Random();

        for(Personaje enemigo : enemigos){
            if (heroes.isEmpty()) break;

            int objetivo = rand.nextInt(heroes.size());
            Personaje heroe = heroes.get(objetivo);

            resultado.append(enemigo.getNombre()).append(" ataca a ").append(heroe.getNombre()).append("...\n");
            enemigo.atacar(heroe);

            if (!heroe.estaVivo()) {
                resultado.append(heroe.getNombre()).append(" ha sido derrotado.\n");
                heroes.remove(objetivo);
            }
        }
        return resultado.toString();
    }

    public boolean hayHeroesVivos(){
        return !heroes.isEmpty();
    }

    public boolean hayEnemigosVivos(){
        return !enemigos.isEmpty();
    }
}
