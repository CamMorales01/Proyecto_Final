package co.edu.unbosque.model;

import java.util.*;

/**
 * Clase que representa una ciudad con un grid de calles, vehículos y semáforos.
 */
public class Ciudad {
    private int filas, columnas;
    private Calle[][] grid;
    private List<Vehiculo> vehiculos;
    private List<Semaforo> semaforos;
    private Random random;
    private int turnoActual;

    /**
     * Constructor de la clase Ciudad.
     * @param filas Número de filas del grid.
     * @param columnas Número de columnas del grid.
    */
    public Ciudad(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grid = new Calle[filas][columnas];
        this.vehiculos = new ArrayList<>();
        this.semaforos = new ArrayList<>();
        this.random = new Random();
        this.turnoActual = 0;
        inicializarCiudad();
    }

    /**
     * Inicializa la ciudad creando calles, semáforos y vehículos.
     */
    private void inicializarCiudad() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Direccion direccion = Direccion.values()[random.nextInt(4)];
                grid[i][j] = new Calle(new Posicion(i, j), direccion);
            }
        }
        int numSemaforos = Math.max(10, (filas * columnas) / 10);
        for (int i = 0; i < numSemaforos; i++) {
            int x, y;
            do {
                x = random.nextInt(filas);
                y = random.nextInt(columnas);
            } while (grid[x][y].tieneSemaforo());

            grid[x][y].agregarSemaforo();
            semaforos.add(grid[x][y].getSemaforo());
        }
        generarVehiculosAleatorios();
    }

    /**
     * Genera vehículos aleatorios en la ciudad.
     */
    private void generarVehiculosAleatorios() {
        int numVehiculos = random.nextInt(filas * columnas / 4) + 5;
        for (int i = 0; i < numVehiculos; i++) {
            int x = random.nextInt(filas);
            int y = random.nextInt(columnas);
            Posicion pos = new Posicion(x, y);
            Direccion dir = grid[x][y].getDireccionFlujo();

            Vehiculo vehiculo = new Vehiculo(pos, dir);
            vehiculos.add(vehiculo);
            grid[x][y].agregarVehiculo(vehiculo);
        }
    }

    /**
     * Simula un turno en la ciudad, actualizando el estado de calles, semáforos y vehículos.
     */
    public void simularTurno() {
        turnoActual++;

        //Actualizar calles y semáforos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                grid[i][j].actualizar();
            }
        }

        moverVehiculos();

        //Generar eventos especiales aleatorios
        if (random.nextDouble() < 0.1) {
            generarEventoEspecial();
        }

        //Agregar nuevos vehículos
        if (random.nextDouble() < 0.3) {
            agregarNuevoVehiculo();
        }
    }

    /**
     * Mueve los vehículos en la ciudad según su velocidad y dirección.
     */
    private void moverVehiculos() {
        List<Vehiculo> vehiculosAMover = new ArrayList<>(vehiculos);

        for (Vehiculo vehiculo : vehiculosAMover) {
            if (vehiculo.isDetenido()) {
                vehiculo.reanudar();
                continue;
            }

            Posicion posActual = vehiculo.getPosicion();
            Calle calleActual = grid[posActual.getX()][posActual.getY()];

            //Verificar obstáculos
            if (calleActual.estaBloqueda()) {
                vehiculo.detener();
                continue;
            }

            //Nueva posición
            Posicion nuevaPos = calcularNuevaPosicion(vehiculo);

            if (nuevaPos != null && esPosicionValida(nuevaPos)) {
                Calle nuevaCalle = grid[nuevaPos.getX()][nuevaPos.getY()];

                //Verificar si hay vehículo adelante
                boolean hayVehiculoAdelante = nuevaCalle.getVehiculos().stream()
                        .anyMatch(v -> v != vehiculo);

                if (hayVehiculoAdelante) {
                    vehiculo.desacelerar();
                } else {
                    //Mover vehículo
                    calleActual.removerVehiculo(vehiculo);
                    vehiculo.setPosicion(nuevaPos);
                    vehiculo.setDireccion(nuevaCalle.getDireccionFlujo());
                    nuevaCalle.agregarVehiculo(vehiculo);
                    vehiculo.acelerar();
                }
            } else {
                //Vehículo sale del mapa
                calleActual.removerVehiculo(vehiculo);
                vehiculos.remove(vehiculo);
            }
        }
    }

    /**
     * Calcula la nueva posición de un vehículo según su dirección y velocidad.
     * @param vehiculo Vehículo a mover.
     * @return Nueva posición del vehículo.
     */
    private Posicion calcularNuevaPosicion(Vehiculo vehiculo) {
        Posicion pos = vehiculo.getPosicion();
        Direccion dir = vehiculo.getDireccion();

        //Avanzar media cuadrícula según la velocidad
        if (vehiculo.getVelocidad() < 0.5) return pos;

        switch (dir) {
            case NORTE: return new Posicion(pos.getX() - 1, pos.getY());
            case SUR: return new Posicion(pos.getX() + 1, pos.getY());
            case ESTE: return new Posicion(pos.getX(), pos.getY() + 1);
            case OESTE: return new Posicion(pos.getX(), pos.getY() - 1);
            default: return pos;
        }
    }

    /**
     * Verifica si una posición es válida dentro del grid de la ciudad.
     * @param pos Posición a verificar.
     * @return true si la posición es válida, false en caso contrario.
     */
    private boolean esPosicionValida(Posicion pos) {
        return pos.getX() >= 0 && pos.getX() < filas && pos.getY() >= 0 && pos.getY() < columnas;
    }

    /**
     * Genera un evento especial aleatorio en la ciudad.
     */
    private void generarEventoEspecial() {
        int x = random.nextInt(filas);
        int y = random.nextInt(columnas);
        TipoEvento tipo = TipoEvento.values()[random.nextInt(TipoEvento.values().length)];

        EventoEspecial evento = new EventoEspecial(tipo, new Posicion(x, y));
        grid[x][y].agregarEvento(evento);
    }

    /**
     * Agrega un nuevo vehículo en una posición aleatoria del borde del mapa.
     */
    private void agregarNuevoVehiculo() {
        int lado = random.nextInt(4);
        int x, y;

        switch (lado) {
            case 0: x = 0; y = random.nextInt(columnas); break; // Arriba
            case 1: x = filas - 1; y = random.nextInt(columnas); break; // Abajo
            case 2: x = random.nextInt(filas); y = 0; break; // Izquierda
            default: x = random.nextInt(filas); y = columnas - 1; break; // Derecha
        }

        Posicion pos = new Posicion(x, y);
        Direccion dir = grid[x][y].getDireccionFlujo();

        Vehiculo vehiculo = new Vehiculo(pos, dir);
        vehiculos.add(vehiculo);
        grid[x][y].agregarVehiculo(vehiculo);
    }

    // Getters
    /**
     * Devuelve el número de filas del grid.
     * @return Número de filas.
     */
    public int getFilas() { return filas; }
    /**
     * Devuelve el número de columnas del grid.
     * @return Número de columnas.
     */
    public int getColumnas() { return columnas; }
    /**
     * Devuelve el grid de calles de la ciudad.
     * @return Matriz de calles.
     */
    public Calle[][] getGrid() { return grid; }
    /**
     * Devuelve la lista de vehículos en la ciudad.
     * @return Lista de vehículos.
     */
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    /**
     * Devuelve la lista de semáforos en la ciudad.
     * @return Lista de semáforos.
     */
    public List<Semaforo> getSemaforos() { return semaforos; }
    /**
     * Devuelve el número de turnos simulados.
     * @return Número de turnos.
     */
    public int getTurnoActual() { return turnoActual; }
}
