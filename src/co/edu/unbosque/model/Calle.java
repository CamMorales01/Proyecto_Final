package co.edu.unbosque.model;

import java.util.*;

/**
 * Clase que representa una calle en el sistema de tráfico.
 * Contiene información sobre la posición, dirección de flujo, vehículos,
 * eventos especiales y semáforos.
 */
public class Calle {
    private Posicion posicion;
    private Direccion direccionFlujo;
    private List<Vehiculo> vehiculos;
    private List<EventoEspecial> eventos;
    private boolean tieneSemaforo;
    private Semaforo semaforo;

    /**
     * Constructor de la clase Calle.
     *
     * @param posicion        La posición de la calle.
     * @param direccionFlujo  La dirección del flujo de tráfico en la calle.
     */
    public Calle(Posicion posicion, Direccion direccionFlujo) {
        this.posicion = posicion;
        this.direccionFlujo = direccionFlujo;
        this.vehiculos = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.tieneSemaforo = false;
    }

    /**
     * Agrega un semáforo a la calle.
     * Crea una nueva instancia de Semáforo en la posición de la calle
     * y marca la calle como que tiene semáforo.
     */
    public void agregarSemaforo() {
        this.tieneSemaforo = true;
        this.semaforo = new Semaforo(posicion);
    }

    /**
     * Agrega un vehículo a la lista de vehículos de la calle.
     *
     * @param vehiculo El vehículo a agregar.
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    /**
     * Remueve un vehículo de la lista de vehículos de la calle.
     *
     * @param vehiculo El vehículo a remover.
     */
    public void removerVehiculo(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
    }

    /**
     * Agrega un evento especial a la lista de eventos de la calle.
     *
     * @param evento El evento especial a agregar.
     */
    public void agregarEvento(EventoEspecial evento) {
        eventos.add(evento);
    }

    /**
     * Verifica si la calle está bloqueada.
     * Una calle se considera bloqueada si hay un accidente o si tiene
     * un semáforo en rojo.
     *
     * @return true si la calle está bloqueada, false en caso contrario.
     */
    public boolean estaBloqueda() {
        for (EventoEspecial evento : eventos) {
            if (evento.getTipo() == TipoEvento.ACCIDENTE) {
                return true;
            }
        }
        return tieneSemaforo && semaforo.getEstado() == EstadoSemaforo.ROJO;
    }

    /**
     * Actualiza el estado de la calle.
     * Actualiza el estado del semáforo si existe y elimina los eventos
     * que ya no están activos.
     */
     public void actualizar() {
            if (tieneSemaforo) {
                semaforo.actualizar();
            }
            eventos.removeIf(evento -> !evento.actualizar());
        }

    // Getters
    /**
     * Obtiene la posición de la calle.
     *
     * @return La posición de la calle.
     */
    public Posicion getPosicion() { return posicion; }
    /**
     * Obtiene la dirección de flujo de la calle.
     *
     * @return La dirección de flujo de la calle.
     */
    public Direccion getDireccionFlujo() { return direccionFlujo; }
    /**
     * Obtiene la lista de vehículos en la calle.
     *
     * @return La lista de vehículos en la calle.
     */
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    /**
     * Obtiene la lista de eventos especiales en la calle.
     *
     * @return La lista de eventos especiales en la calle.
     */
    public List<EventoEspecial> getEventos() { return eventos; }
    /**
     * Verifica si la calle tiene un semáforo.
     *
     * @return true si la calle tiene un semáforo, false en caso contrario.
     */
    public boolean tieneSemaforo() { return tieneSemaforo; }
    /**
     * Obtiene el semáforo de la calle.
     *
     * @return El semáforo de la calle.
     */
    public Semaforo getSemaforo() { return semaforo; }
}
