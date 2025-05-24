package co.edu.unbosque.model;

import java.util.Objects;

/**
 * Clase que representa una posición en un plano bidimensional.
 * Esta clase permite manejar las coordenadas x e y de un punto en el sistema
 * de coordenadas cartesianas.
 */
public class Posicion {
    private int x, y;

    /**
     * Constructor que crea una nueva posición con las coordenadas especificadas.
     *
     * @param x  La coordenada en el eje horizontal
     * @param y  La coordenada en el eje vertical
     */
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la coordenada X de la posición.
     *
     * @return El valor de la coordenada X
     */
    public int getX() { return x; }
    /**
     * Obtiene la coordenada Y de la posición.
     *
     * @return El valor de la coordenada Y
     */
    public int getY() { return y; }
    /**
     * Establece un nuevo valor para la coordenada X.
     *
     * @param x El nuevo valor para la coordenada X
     */
    public void setX(int x) { this.x = x; }
    /**
     * Establece un nuevo valor para la coordenada Y.
     *
     * @param y El nuevo valor para la coordenada Y
     */
    public void setY(int y) { this.y = y; }

    /**
     * Compara esta posición con otro objeto para determinar si son iguales.
     * Dos posiciones son iguales si tienen las mismas coordenadas X e Y.
     *
     * @param obj El objeto a comparar con esta posición
     * @return true si las posiciones son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posicion posicion = (Posicion) obj;
        return x == posicion.x && y == posicion.y;
    }

    /**
     * Genera un código hash para esta posición basado en sus coordenadas.
     *
     * @return El código hash calculado para esta posición
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
