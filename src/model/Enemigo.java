package model;

import java.util.Random;

public class Enemigo extends Personaje {
    private TipoEnemigo tipoEnemigo;
    private Random random = new Random();

    public Enemigo(TipoEnemigo tipoEnemigo, String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        super(TipoPersonaje.ENEMIGO, nombre, hp, mp, ataque, defensa, velocidad);
        this.tipoEnemigo = tipoEnemigo;
    }

    public TipoEnemigo getTipoEnemigo() {
        return tipoEnemigo;
    }

    public String ataqueNormal(Personaje objetivo) {
        if (!estaVivo()) return getNombre() + " esta muerto y no puede atacar.\n";
        if (getEstado() == Estado.DORMIDO) {
            reducirTurnosEstado();
            return getNombre() + " esta dormido y pierde el turno.\n";
        }
        int dano = atacar(objetivo);
        int hpRestante = objetivo.getHp();
        return getNombre() + " ataca a " + objetivo.getNombre() + " causando " + dano + " de dano. " +
               objetivo.getNombre() + " HP restante: " + hpRestante + ".\n";
    }

    public String ataqueSlime(Personaje objetivo) {
        if (tipoEnemigo != TipoEnemigo.SLIME) return "";
        int dano = 3 + random.nextInt(4);
        objetivo.recibirDanio(dano);
        return getNombre() + " usa Ataque basico y causa " + dano + " de dano a " + objetivo.getNombre() +
               ". HP restante: " + objetivo.getHp() + ".\n";
    }

    public String picotazo(Personaje objetivo) {
        if (tipoEnemigo != TipoEnemigo.DRACKY) return "";
        double prob = Math.random();
        int dano = 5 + random.nextInt(4);
        if (prob <= 0.7) {
            objetivo.recibirDanio(dano);
            return getNombre() + " usa Picotazo y acierta causando " + dano + " de dano a " + objetivo.getNombre() +
                   ". HP restante: " + objetivo.getHp() + ".\n";
        } else {
            return getNombre() + " intenta Picotazo y falla.\n";
        }
    }

    public String golpeGarrote(Personaje objetivo) {
        if (tipoEnemigo != TipoEnemigo.PATYPUNK) return "";
        int dano = 6 + random.nextInt(5);
        objetivo.recibirDanio(dano);
        return getNombre() + " usa Golpe con Garrote y causa " + dano + " de dano a " + objetivo.getNombre() +
               ". HP restante: " + objetivo.getHp() + ".\n";
    }

    public String patadaGiratoria(Personaje objetivo) {
        if (tipoEnemigo != TipoEnemigo.SPIKED_HARE) return "";
        int dano = 6 + random.nextInt(4);
        if (Math.random() <= 0.3) {
            dano = (int)(dano * 1.2);
            objetivo.recibirDanio(dano);
            return getNombre() + " usa Patada Giratoria potenciada y causa " + dano + " de dano a " + objetivo.getNombre() +
                   ". HP restante: " + objetivo.getHp() + ".\n";
        } else {
            objetivo.recibirDanio(dano);
            return getNombre() + " usa Patada Giratoria y causa " + dano + " de dano a " + objetivo.getNombre() +
                   ". HP restante: " + objetivo.getHp() + ".\n";
        }
    }

    public String sleepAttack(Personaje objetivo) {
        if (tipoEnemigo != TipoEnemigo.TERROR_TABBY) return "";
        int dano = 4 + random.nextInt(4);
        objetivo.recibirDanio(dano);
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append(" usa Sleep Attack y causa ").append(dano).append(" de dano a ")
          .append(objetivo.getNombre()).append(". HP restante: ").append(objetivo.getHp()).append(".\n");
        if (Math.random() <= 0.9) {
            objetivo.aplicarEstado(Estado.DORMIDO, 2);
            sb.append(objetivo.getNombre()).append(" queda dormido por 2 turnos.\n");
        } else {
            sb.append(objetivo.getNombre()).append(" resistio el sueño.\n");
        }
        return sb.toString();
    }
}
