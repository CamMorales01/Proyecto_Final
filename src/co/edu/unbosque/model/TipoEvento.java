package co.edu.unbosque.model;

/**
 * Enumeración que define los diferentes tipos de eventos especiales que pueden ocurrir
 * en el sistema de tráfico, cada uno con una duración específica en turnos.
 */
public enum TipoEvento {
    /**
     * Representa un accidente en la vía.
     * Tiene una duración de 3 turnos, siendo el evento más prolongado
     * debido a su mayor impacto en el tráfico.
     */
    ACCIDENTE(3),

    /**
     * Representa una condición especial del estado de la vía
     * (por ejemplo, obras, pavimento dañado).
     * Tiene una duración intermedia de 2 turnos.
     */
    ESTADO_VIA(2),

    /**
     * Representa una congestión o embotellamiento temporal.
     * Es el evento más breve con duración de 1 turno.
     */
    TRAFICO(1);

    private final int duracion;

    /**
     * Constructor del enum que establece la duración del tipo de evento.
     *
     * @param duracion  Número de turnos que durará el evento
     */
    TipoEvento(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene la duración en turnos del tipo de evento.
     *
     * @return El número de turnos que dura este tipo de evento
     */
    public int getDuracion() {
        return duracion;
    }
}
