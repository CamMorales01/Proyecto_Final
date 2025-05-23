package co.edu.unbosque.controller;

import co.edu.unbosque.model.Ciudad;
import co.edu.unbosque.view.VistaSimulacion;

public class ControladorSimulacion {

    private Ciudad modelo;
    private VistaSimulacion vista;
    private boolean simulacionActiva;

    public ControladorSimulacion(VistaSimulacion vista) {
        this.vista = vista;
        this.simulacionActiva = false;
        vista.setControlador(this);
    }

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

    public void resetearSimulacion() {
        simulacionActiva = false;
        vista.pararTimer();
        modelo = null;
        // La vista se limpiará automáticamente
    }

}
