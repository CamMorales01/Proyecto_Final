package co.edu.unbosque;

import co.edu.unbosque.view.VistaSimulacion;
import co.edu.unbosque.controller.ControladorSimulacion;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SimulacionTrafico {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaSimulacion vista = new VistaSimulacion();
            ControladorSimulacion controlador = new ControladorSimulacion(vista);

            vista.setVisible(true);
        });
    }
}
