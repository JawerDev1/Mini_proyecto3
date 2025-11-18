package model;

import java.util.Random;

public class Jugador extends Personaje {
    private TipoHeroe tipoHeroe;
    private Random random = new Random();

    public Jugador(TipoHeroe tipoHeroe, String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(TipoPersonaje.HEROE, nombre, hp, mp, ataque, defensa, velocidad);
        this.tipoHeroe = tipoHeroe;
    }

    public TipoHeroe getTipoHeroe() {
        return tipoHeroe;
    }

    // Habilidades y acciones devuelven Strings descriptivos para que el controlador arme el log

    public String ataqueNormal(Personaje objetivo) {
        if (!estaVivo()) return getNombre() + " esta muerto y no puede atacar.\n";
        if (getEstado() == Estado.DORMIDO) return getNombre() + " esta dormido y no puede atacar.\n";

        int dano = atacar(objetivo);
        int hpRestante = objetivo.getHp();
        return getNombre() + " ataca a " + objetivo.getNombre() + " causando " + dano + " de dano. " +
               objetivo.getNombre() + " HP restante: " + hpRestante + ".\n";
    }

    public String cura12() {
        if (getMp() >= 3) {
            gastarMp(3);
            int hp = curar(12);
            return getNombre() + " usa Cura y recupera 12 HP. HP actual: " + hp + ".\n";
        } else {
            return getNombre() + " no tiene suficiente MP para usar Cura.\n";
        }
    }

    public String cura15() {
        if (getMp() >= 4) {
            gastarMp(4);
            int hp = curar(15);
            return getNombre() + " usa Cura avanzada y recupera 15 HP. HP actual: " + hp + ".\n";
        } else {
            return getNombre() + " no tiene suficiente MP para usar Cura avanzada.\n";
        }
    }

    public String frizz(Personaje objetivo) {
        if (getMp() >= 4) {
            gastarMp(4);
            int dano = random.nextInt(6) + 10; // 10-15
            objetivo.recibirDanio(dano);
            int hpRestante = objetivo.getHp();
            return getNombre() + " lanza Frizz e inflige " + dano + " de dano magico a " + objetivo.getNombre() +
                   ". " + objetivo.getNombre() + " HP restante: " + hpRestante + ".\n";
        } else {
            return getNombre() + " no tiene suficiente MP para lanzar Frizz.\n";
        }
    }

    public String golpePoderoso(Personaje objetivo) {
        int prob = random.nextInt(100) + 1;
        if (prob <= 30) {
            int danoBase = Math.max(1, getAtaque() - objetivo.getDefensa());
            int danoTotal = (int) (danoBase * 1.5);
            objetivo.recibirDanio(danoTotal);
            int hpRestante = objetivo.getHp();
            return getNombre() + " usa Golpe Poderoso e inflige " + danoTotal + " de dano a " + objetivo.getNombre() +
                   ". " + objetivo.getNombre() + " HP restante: " + hpRestante + ".\n";
        } else {
            return getNombre() + " intenta Golpe Poderoso y falla.\n";
        }
    }

    public String usarHabilidadEspecial(Personaje objetivo) {
        // CORRECCION: asignar habilidades al heroe correcto
        switch (tipoHeroe) {
            case HEROE:
                return cura12();
            case YANGUS:
                // Yangus debe usar Golpe Poderoso
                if (objetivo != null && objetivo.estaVivo()) return golpePoderoso(objetivo);
                else return getNombre() + " no tiene un objetivo valido para Golpe Poderoso.\n";
            case YESSICA:
                if (objetivo != null && objetivo.estaVivo()) return frizz(objetivo);
                else return getNombre() + " no tiene un objetivo valido para Frizz.\n";
            case ANGELO:
                // Angelo debe curar
                return cura15();
            default:
                return getNombre() + " no tiene una habilidad especial definida.\n";
        }
    }
}
