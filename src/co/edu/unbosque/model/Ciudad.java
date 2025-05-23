package co.edu.unbosque.model;

import java.util.*;

public class Ciudad {
    private int filas, columnas;
    private Calle[][] grid;
    private List<Vehiculo> vehiculos;
    private List<Semaforo> semaforos;
    private Random random;
    private int turnoActual;

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

    private void inicializarCiudad() {
        // Inicializar calles con direcciones aleatorias
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Direccion direccion = Direccion.values()[random.nextInt(4)];
                grid[i][j] = new Calle(new Posicion(i, j), direccion);
            }
        }

        // Colocar semáforos (mínimo 10)
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

        // Generar vehículos iniciales
        generarVehiculosAleatorios();
    }

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

    public void simularTurno() {
        turnoActual++;

        // Actualizar calles y semáforos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                grid[i][j].actualizar();
            }
        }

        // Mover vehículos
        moverVehiculos();

        // Generar eventos especiales aleatorios
        if (random.nextDouble() < 0.1) { // 10% probabilidad por turno
            generarEventoEspecial();
        }

        // Agregar nuevos vehículos ocasionalmente
        if (random.nextDouble() < 0.3) { // 30% probabilidad por turno
            agregarNuevoVehiculo();
        }
    }

    private void moverVehiculos() {
        List<Vehiculo> vehiculosAMover = new ArrayList<>(vehiculos);

        for (Vehiculo vehiculo : vehiculosAMover) {
            if (vehiculo.isDetenido()) {
                vehiculo.reanudar();
                continue;
            }

            Posicion posActual = vehiculo.getPosicion();
            Calle calleActual = grid[posActual.getX()][posActual.getY()];

            // Verificar obstáculos
            if (calleActual.estaBloqueda()) {
                vehiculo.detener();
                continue;
            }

            // Calcular nueva posición
            Posicion nuevaPos = calcularNuevaPosicion(vehiculo);

            if (nuevaPos != null && esPosicionValida(nuevaPos)) {
                Calle nuevaCalle = grid[nuevaPos.getX()][nuevaPos.getY()];

                // Verificar si hay vehículo adelante
                boolean hayVehiculoAdelante = nuevaCalle.getVehiculos().stream()
                        .anyMatch(v -> v != vehiculo);

                if (hayVehiculoAdelante) {
                    vehiculo.desacelerar();
                } else {
                    // Mover vehículo
                    calleActual.removerVehiculo(vehiculo);
                    vehiculo.setPosicion(nuevaPos);
                    vehiculo.setDireccion(nuevaCalle.getDireccionFlujo());
                    nuevaCalle.agregarVehiculo(vehiculo);
                    vehiculo.acelerar();
                }
            } else {
                // Vehículo sale del mapa
                calleActual.removerVehiculo(vehiculo);
                vehiculos.remove(vehiculo);
            }
        }
    }

    private Posicion calcularNuevaPosicion(Vehiculo vehiculo) {
        Posicion pos = vehiculo.getPosicion();
        Direccion dir = vehiculo.getDireccion();

        // Avanzar media cuadrícula según la velocidad
        if (vehiculo.getVelocidad() < 0.5) return pos; // No se mueve si es muy lento

        switch (dir) {
            case NORTE: return new Posicion(pos.getX() - 1, pos.getY());
            case SUR: return new Posicion(pos.getX() + 1, pos.getY());
            case ESTE: return new Posicion(pos.getX(), pos.getY() + 1);
            case OESTE: return new Posicion(pos.getX(), pos.getY() - 1);
            default: return pos;
        }
    }

    private boolean esPosicionValida(Posicion pos) {
        return pos.getX() >= 0 && pos.getX() < filas && pos.getY() >= 0 && pos.getY() < columnas;
    }

    private void generarEventoEspecial() {
        int x = random.nextInt(filas);
        int y = random.nextInt(columnas);
        TipoEvento tipo = TipoEvento.values()[random.nextInt(TipoEvento.values().length)];

        EventoEspecial evento = new EventoEspecial(tipo, new Posicion(x, y));
        grid[x][y].agregarEvento(evento);
    }

    private void agregarNuevoVehiculo() {
        // Agregar vehículo en el borde del mapa
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
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
    public Calle[][] getGrid() { return grid; }
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    public List<Semaforo> getSemaforos() { return semaforos; }
    public int getTurnoActual() { return turnoActual; }
}
