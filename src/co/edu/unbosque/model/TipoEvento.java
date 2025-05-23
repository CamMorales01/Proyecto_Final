package co.edu.unbosque.model;

public enum TipoEvento {
    ACCIDENTE(3), ESTADO_VIA(2), TRAFICO(1);

    private final int duracion;

    TipoEvento(int duracion) {
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }
}
