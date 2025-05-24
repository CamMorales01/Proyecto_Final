package co.edu.unbosque.model;

/**
 * Clase que representa un vehículo en el sistema de simulación de tráfico.
 * Cada vehículo tiene una posición, dirección, velocidad y estado de movimiento.
 */
public class Vehiculo {
    private static int contadorId = 1;
    private int id;
    private Posicion posicion;
    private Direccion direccion;
    private double velocidad;
    private double velocidadMaxima;
    private boolean detenido;

    /**
     * Constructor que crea un nuevo vehículo con una posición y dirección inicial.
     *
     * @param posicion   La posición inicial del vehículo en el sistema
     * @param direccion  La dirección inicial hacia la que se mueve el vehículo
     */
    public Vehiculo(Posicion posicion, Direccion direccion) {
        this.id = contadorId++;
        this.posicion = posicion;
        this.direccion = direccion;
        this.velocidadMaxima = 1.0; // 1 cuadricula por turno máximo
        this.velocidad = velocidadMaxima;
        this.detenido = false;
    }

    // Getters y setters
    /**
     * Obtiene la posición actual del vehículo.
     *
     * @return La posición actual del vehículo
     */
    public Posicion getPosicion() { return posicion; }
    /**
     * Establece la nueva posición del vehículo.
     *
     * @param posicion La nueva posición del vehículo
     */
    public void setPosicion(Posicion posicion) { this.posicion = posicion; }
    /**
     * Obtiene la dirección actual del vehículo.
     *
     * @return La dirección actual del vehículo
     */
    public Direccion getDireccion() { return direccion; }
    /**
     * Establece la nueva dirección del vehículo.
     *
     * @param direccion La nueva dirección del vehículo
     */
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    /**
     * Obtiene la velocidad actual del vehículo.
     *
     * @return La velocidad actual en cuadrículas por turno
     */
    public double getVelocidad() { return velocidad; }
    /**
     * Establece la velocidad del vehículo, limitada por la velocidad máxima.
     *
     * @param velocidad La nueva velocidad del vehículo
     */
    public void setVelocidad(double velocidad) { this.velocidad = Math.max(0, Math.min(velocidad, velocidadMaxima)); }
    /**
     * Verifica si el vehículo está detenido.
     *
     * @return true si el vehículo está detenido, false en caso contrario
     */
    public boolean isDetenido() { return detenido; }
    /**
     * Establece el estado de detención del vehículo.
     *
     * @param detenido true si el vehículo está detenido, false en caso contrario
     */
    public void setDetenido(boolean detenido) { this.detenido = detenido; }

    /**
     * Aumenta gradualmente la velocidad del vehículo hasta su velocidad máxima.
     */
    public void acelerar() {
        if (velocidad < velocidadMaxima) {
            velocidad = Math.min(velocidad + 0.1, velocidadMaxima);
        }
    }

    /**
     * Disminuye gradualmente la velocidad del vehículo hasta detenerlo.
     */
    public void desacelerar() {
        velocidad = Math.max(velocidad - 0.2, 0);
    }

    /**
     * Detiene completamente el vehículo, estableciendo su velocidad a 0
     * y marcándolo como detenido.
     */
    public void detener() {
        velocidad = 0;
        detenido = true;
    }

    /**
     * Reanuda el movimiento del vehículo, estableciendo su velocidad a un valor inicial.
     */
    public void reanudar() {
        detenido = false;
        velocidad = 0.5;
    }
}
