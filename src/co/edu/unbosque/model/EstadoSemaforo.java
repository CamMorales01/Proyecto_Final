package co.edu.unbosque.model;
/**
 * Enumeración que representa los estados posibles de un semáforo en el sistema de tráfico.
 * Define los estados básicos que controlan el flujo de vehículos en las intersecciones.
 */
public enum EstadoSemaforo {
    /**
     * Representa el estado ROJO del semáforo.
     * Indica que los vehículos deben detenerse completamente y no pueden avanzar
     * por la intersección.
     */
    ROJO,

    /**
     * Representa el estado VERDE del semáforo.
     * Indica que los vehículos tienen permitido avanzar y circular libremente
     * por la intersección.
     */
    VERDE
}
