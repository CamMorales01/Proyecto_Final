package co.edu.unbosque.model;

public class Vehiculo {
    private static int contadorId = 1;
    private int id;
    private Posicion posicion;
    private Direccion direccion;
    private double velocidad;
    private double velocidadMaxima;
    private boolean detenido;

    public Vehiculo(Posicion posicion, Direccion direccion) {
        this.id = contadorId++;
        this.posicion = posicion;
        this.direccion = direccion;
        this.velocidadMaxima = 1.0; // 1 cuadricula por turno máximo
        this.velocidad = velocidadMaxima;
        this.detenido = false;
    }

    // Getters y setters
    public int getId() { return id; }
    public Posicion getPosicion() { return posicion; }
    public void setPosicion(Posicion posicion) { this.posicion = posicion; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public double getVelocidad() { return velocidad; }
    public void setVelocidad(double velocidad) { this.velocidad = Math.max(0, Math.min(velocidad, velocidadMaxima)); }
    public boolean isDetenido() { return detenido; }
    public void setDetenido(boolean detenido) { this.detenido = detenido; }

    public void acelerar() {
        if (velocidad < velocidadMaxima) {
            velocidad = Math.min(velocidad + 0.1, velocidadMaxima);
        }
    }

    public void desacelerar() {
        velocidad = Math.max(velocidad - 0.2, 0);
    }

    public void detener() {
        velocidad = 0;
        detenido = true;
    }

    public void reanudar() {
        detenido = false;
        velocidad = 0.5; // Velocidad inicial al reanudar
    }
}
