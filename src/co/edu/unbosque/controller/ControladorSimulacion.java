package co.edu.unbosque.controller;

import co.edu.unbosque.model.Ciudad;
import co.edu.unbosque.view.VistaSimulacion;

/**
 * ControladorSimulacion es la clase encargada de manejar la lógica de la simulación
 * y la interacción entre el modelo (Ciudad) y la vista (VistaSimulacion).
 */
public class ControladorSimulacion {

    private Ciudad modelo;
    private VistaSimulacion vista;
    private boolean simulacionActiva;

    /**
     * Constructor de la clase ControladorSimulacion.
     * Inicializa la vista y establece el controlador.
     *
     * @param vista La vista de la simulación.
     */
    public ControladorSimulacion(VistaSimulacion vista) {
        this.vista = vista;
        this.simulacionActiva = false;
        vista.setControlador(this);
    }

    /**
     * Método para iniciar la simulación.
     * Verifica las dimensiones de la ciudad y comienza la simulación si son válidas.
     */
    public void iniciarSimulacion() {
        if (!simulacionActiva) {
            int filas = vista.getFilas();
            int columnas = vista.getColumnas();

            if (filas < 5 || columnas < 5) {
                vista.mostrarMensaje("Las dimensiones mínimas son 5x5");
                return;
            }

            modelo = new Ciudad(filas, columnas);
            simulacionActiva = true;

            vista.iniciarTimer(e -> {
                if (simulacionActiva && modelo != null) {
                    modelo.simularTurno();
                    vista.actualizarVista(modelo);
                }
            });

            vista.actualizarVista(modelo);
        }
    }

    /**
     * Método para pausar o reanudar la simulación.
     * Cambia el estado de la simulación activa y actualiza la vista.
     */
    public void pausarSimulacion() {
        simulacionActiva = !simulacionActiva;
        if (!simulacionActiva) {
            vista.pararTimer();
        } else if (modelo != null) {
            vista.iniciarTimer(e -> {
                if (simulacionActiva && modelo != null) {
                    modelo.simularTurno();
                    vista.actualizarVista(modelo);
                }
            });
        }
    }

    /**
     * Método para reiniciar la simulación.
     * Detiene la simulación activa y limpia la vista.
     */
    public void resetearSimulacion() {
        simulacionActiva = false;
        vista.pararTimer();
        modelo = null;
    }

}
