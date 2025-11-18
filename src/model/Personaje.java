package model;

public class Personaje {
    private TipoPersonaje tipo;
    private String nombre;
    private int hp;
    private int mp;
    private int ataque;
    private int defensa;
    private int velocidad;

    // Estado
    private Estado estado = Estado.NORMAL;
    private int turnosEstado = 0;

    public Personaje(TipoPersonaje tipo, String nombre, int hp, int mp, int ataque, int defensa, int velocidad) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.hp = hp;
        this.mp = mp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
    }

    // Getters
    public TipoPersonaje getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public int getHp() { return hp; }
    public int getMp() { return mp; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public Estado getEstado() { return estado; }

    // Estado methods
    public void setEstado(Estado nuevoEstado, int turnos) {
        this.estado = nuevoEstado;
        this.turnosEstado = turnos;
    }

    public void aplicarEstado(Estado nuevoEstado, int turnos) {
        setEstado(nuevoEstado, turnos);
        if (nuevoEstado != Estado.NORMAL) {
            System.out.println(nombre + " ahora esta " + nuevoEstado + " por " + turnos + " turnos.");
        } else {
            System.out.println(nombre + " ha vuelto a la normalidad.");
        }
    }

    public void reducirTurnosEstado() {
        if (estado != Estado.NORMAL) {
            turnosEstado--;
            if (turnosEstado <= 0) {
                estado = Estado.NORMAL;
                System.out.println(nombre + " se ha recuperado del estado alterado.");
            }
        }
    }

    // Combate: atacar devuelve el dano aplicado (int) o 0 si no pudo actuar
    public int atacar(Personaje enemigo) {
        if (estado == Estado.DORMIDO) {
            // no ataca
            return 0;
        }
        int danio = this.ataque - (enemigo.getDefensa() / 2);
        if (danio < 1) danio = 1;
        enemigo.recibirDanio(danio);
        return danio;
    }

    // recibirDanio reduce hp y devuelve hp restante
    public int recibirDanio(int danio) {
        hp -= danio;
        if (hp < 0) hp = 0;
        return hp;
    }

    // gastarMp reduce mp y devuelve mp restante
    public int gastarMp(int cantidad) {
        mp -= cantidad;
        if (mp < 0) mp = 0;
        return mp;
    }

    // curar suma hp y devuelve hp actual
    public int curar(int cantidad) {
        hp += cantidad;
        return hp;
    }

    public boolean estaVivo() {
        return hp > 0;
    }

    // Util: mostrar estado (consola)
    public void mostrarEstado() {
        System.out.println("------------------------------");
        System.out.println("Estado de " + nombre);
        System.out.println("Tipo: " + tipo);
        System.out.println("HP: " + hp);
        System.out.println("MP: " + mp);
        System.out.println("Ataque: " + ataque);
        System.out.println("Defensa: " + defensa);
        System.out.println("Velocidad: " + velocidad);
        System.out.println("Estado: " + estado);
        System.out.println("------------------------------");
    }
}
