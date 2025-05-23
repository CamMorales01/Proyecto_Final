package co.edu.unbosque.model;

import java.util.*;

public class Calle {
    private Posicion posicion;
    private Direccion direccionFlujo;
    private List<Vehiculo> vehiculos;
    private List<EventoEspecial> eventos;
    private boolean tieneSemaforo;
    private Semaforo semaforo;

    public Calle(Posicion posicion, Direccion direccionFlujo) {
        this.posicion = posicion;
        this.direccionFlujo = direccionFlujo;
        this.vehiculos = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.tieneSemaforo = false;
    }

    public void agregarSemaforo() {
        this.tieneSemaforo = true;
        this.semaforo = new Semaforo(posicion);
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public void removerVehiculo(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
    }

    public void agregarEvento(EventoEspecial evento) {
        eventos.add(evento);
    }

    public boolean estaBloqueda() {
        for (EventoEspecial evento : eventos) {
            if (evento.getTipo() == TipoEvento.ACCIDENTE) {
                return true;
            }
        }
        return tieneSemaforo && semaforo.getEstado() == EstadoSemaforo.ROJO;
    }

    public void actualizar() {
        // Actualizar semáforo
        if (tieneSemaforo) {
            semaforo.actualizar();
        }

        // Actualizar eventos
        eventos.removeIf(evento -> !evento.actualizar());
    }

    // Getters
    public Posicion getPosicion() { return posicion; }
    public Direccion getDireccionFlujo() { return direccionFlujo; }
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    public List<EventoEspecial> getEventos() { return eventos; }
    public boolean tieneSemaforo() { return tieneSemaforo; }
    public Semaforo getSemaforo() { return semaforo; }
}
