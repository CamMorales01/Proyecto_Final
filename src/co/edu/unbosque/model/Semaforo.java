package co.edu.unbosque.model;

/**
 * Clase que representa un semáforo en el sistema de tráfico.
 * Controla el flujo de vehículos alternando entre estados VERDE y ROJO
 * en intervalos de tiempo definidos.
 */
public class Semaforo {
    private Posicion posicion;
    private EstadoSemaforo estado;
    private int tiempoRestante;
    private static final int TIEMPO_CAMBIO = 2;

    /**
     * Constructor que crea un nuevo semáforo en una posición específica.
     * El estado inicial se determina de manera aleatoria (50% probabilidad para cada estado).
     *
     * @param posicion  La ubicación donde se encuentra el semáforo en el sistema
     */
    public Semaforo(Posicion posicion) {
        this.posicion = posicion;
        this.estado = Math.random() < 0.5 ? EstadoSemaforo.VERDE : EstadoSemaforo.ROJO;
        this.tiempoRestante = TIEMPO_CAMBIO;
    }

    /**
     * Método que actualiza el estado del semáforo y el tiempo restante.
     * Si el tiempo restante llega a cero, se cambia el estado del semáforo.
     */
    public void actualizar() {
        tiempoRestante--;
        if (tiempoRestante <= 0) {
            cambiarEstado();
            tiempoRestante = TIEMPO_CAMBIO;
        }
    }

    /**
     * Método que cambia el estado del semáforo.
     * Alterna entre VERDE y ROJO.
     */
    private void cambiarEstado() {
        estado = (estado == EstadoSemaforo.VERDE) ? EstadoSemaforo.ROJO : EstadoSemaforo.VERDE;
    }

    // Getters
    /**
     * Obtiene la posición actual del semáforo.
     *
     * @return La posición donde está ubicado el semáforo
     */
    public Posicion getPosicion() { return posicion; }
    /**
     * Obtiene la posición actual del semáforo.
     *
     * @return La posición donde está ubicado el semáforo
     */
    public EstadoSemaforo getEstado() { return estado; }
    /**
     * Obtiene la posición actual del semáforo.
     *
     * @return La posición donde está ubicado el semáforo
     */
    public int getTiempoRestante() { return tiempoRestante; }
}
