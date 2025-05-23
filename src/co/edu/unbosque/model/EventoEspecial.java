package co.edu.unbosque.model;

public class EventoEspecial {
    private TipoEvento tipo;
    private Posicion posicion;
    private int turnosRestantes;

    public EventoEspecial(TipoEvento tipo, Posicion posicion) {
        this.tipo = tipo;
        this.posicion = posicion;
        this.turnosRestantes = tipo.getDuracion();
    }

    public boolean actualizar() {
        turnosRestantes--;
        return turnosRestantes > 0;
    }

    // Getters
    public TipoEvento getTipo() { return tipo; }
    public Posicion getPosicion() { return posicion; }
    public int getTurnosRestantes() { return turnosRestantes; }
}
