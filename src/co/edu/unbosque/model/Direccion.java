package co.edu.unbosque.model;

/**
 * Enumeración que representa las direcciones cardinales en el sistema de tráfico.
 * Define las cuatro direcciones principales en las que puede fluir el tráfico en una calle.
 */
public enum Direccion {
    /**
     * Representa la dirección hacia el Norte.
     * Indica que el flujo del tráfico se mueve hacia arriba en el sistema.
     */
    NORTE,

    /**
     * Representa la dirección hacia el Sur.
     * Indica que el flujo del tráfico se mueve hacia abajo en el sistema.
     */
    SUR,

    /**
     * Representa la dirección hacia el Este.
     * Indica que el flujo del tráfico se mueve hacia la derecha en el sistema.
     */
    ESTE,

    /**
     * Representa la dirección hacia el Oeste.
     * Indica que el flujo del tráfico se mueve hacia la izquierda en el sistema.
     */
    OESTE
}