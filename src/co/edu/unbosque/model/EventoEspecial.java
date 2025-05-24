package co.edu.unbosque.model;

/**
 * Clase que representa un evento especial en el sistema de tráfico.
 * Los eventos especiales son situaciones temporales que afectan el flujo normal
 * del tráfico, como accidentes o bloqueos, y tienen una duración limitada en turnos.
 */
public class EventoEspecial {
    private TipoEvento tipo;
    private Posicion posicion;
    private int turnosRestantes;

    /**
     * Constructor para crear un nuevo evento especial.
     *
     * @param tipo     El tipo de evento especial (accidente, bloqueo, etc.).
     * @param posicion La posición en el mapa donde ocurre el evento.
     */
    public EventoEspecial(TipoEvento tipo, Posicion posicion) {
        this.tipo = tipo;
        this.posicion = posicion;
        this.turnosRestantes = tipo.getDuracion();
    }

    /**
     * Método para actualizar el evento especial.
     *
     * @return true si el evento sigue activo, false si ha terminado.
     */
    public boolean actualizar() {
        turnosRestantes--;
        return turnosRestantes > 0;
    }

    // Getters
    /**
     * Obtiene el tipo de evento especial.
     *
     * @return El tipo de evento especial.
     */
    public TipoEvento getTipo() { return tipo; }
    /**
     * Obtiene la posición del evento especial en el mapa.
     *
     * @return La posición del evento especial.
     */
    public Posicion getPosicion() { return posicion; }
    /**
     * Obtiene los turnos restantes para que el evento especial termine.
     *
     * @return El número de turnos restantes.
     */
    public int getTurnosRestantes() { return turnosRestantes; }
}
