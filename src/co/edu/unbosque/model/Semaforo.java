package co.edu.unbosque.model;

public class Semaforo {
    private Posicion posicion;
    private EstadoSemaforo estado;
    private int tiempoRestante;
    private static final int TIEMPO_CAMBIO = 2;

    public Semaforo(Posicion posicion) {
        this.posicion = posicion;
        this.estado = Math.random() < 0.5 ? EstadoSemaforo.VERDE : EstadoSemaforo.ROJO;
        this.tiempoRestante = TIEMPO_CAMBIO;
    }

    public void actualizar() {
        tiempoRestante--;
        if (tiempoRestante <= 0) {
            cambiarEstado();
            tiempoRestante = TIEMPO_CAMBIO;
        }
    }

    private void cambiarEstado() {
        estado = (estado == EstadoSemaforo.VERDE) ? EstadoSemaforo.ROJO : EstadoSemaforo.VERDE;
    }

    // Getters
    public Posicion getPosicion() { return posicion; }
    public EstadoSemaforo getEstado() { return estado; }
    public int getTiempoRestante() { return tiempoRestante; }
}
